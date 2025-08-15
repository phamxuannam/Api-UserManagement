package com.phxnnam.Api_UserManagement.service.imp;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.phxnnam.Api_UserManagement.dto.request.AuthenticateRequest;
import com.phxnnam.Api_UserManagement.dto.request.IntrospectRequest;
import com.phxnnam.Api_UserManagement.dto.request.LogoutRequest;
import com.phxnnam.Api_UserManagement.dto.request.RefreshRequest;
import com.phxnnam.Api_UserManagement.dto.response.AuthenticateResponse;
import com.phxnnam.Api_UserManagement.dto.response.IntrospectResponse;
import com.phxnnam.Api_UserManagement.dto.response.RefreshResponse;
import com.phxnnam.Api_UserManagement.entity.TokenExpEntity;
import com.phxnnam.Api_UserManagement.entity.UserEntity;
import com.phxnnam.Api_UserManagement.repository.TokenExpRepository;
import com.phxnnam.Api_UserManagement.repository.UserRepository;
import com.phxnnam.Api_UserManagement.service.IAuthenticateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateService implements IAuthenticateService {

    UserRepository userRepository;
    TokenExpRepository tokenExpRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    long REFRESHABLE_DURATION;

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("User doesn't exists."));
        boolean checkPass = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!checkPass && (user.getIsActive() != 1)) throw new RuntimeException("Unauthenticated.");
        String token = generateToken(user);
        return AuthenticateResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    @Override
    public String generateToken(UserEntity user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("npx")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", getRole(user.getUsername()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRole(String username) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        UserEntity user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User doesn't exists."));
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(roles -> stringJoiner.add(roles.getName()));
        }
        return stringJoiner.toString();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verify(request.getToken(), true);
        String id = signToken.getJWTClaimsSet().getJWTID();
        Date exp = signToken.getJWTClaimsSet().getExpirationTime();
        TokenExpEntity tokenE = TokenExpEntity.builder()
                .id(id)
                .ExpiryTime(exp)
                .build();
        tokenExpRepository.save(tokenE);
    }

    @Override
    public RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        SignedJWT signToken = verify(request.getToken(), true);

        String id = signToken.getJWTClaimsSet().getJWTID();
        Date exp  = new Date(signToken.getJWTClaimsSet().getExpirationTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli());
        TokenExpEntity tokenE = TokenExpEntity.builder()
                .id(id)
                .ExpiryTime(exp)
                .build();
        tokenExpRepository.save(tokenE);

        UserEntity user = userRepository.findByUsername(signToken.getJWTClaimsSet().getSubject())
                .orElseThrow( () -> new RuntimeException("User doesn't exists."));

        String token = generateToken(user);
        return RefreshResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public SignedJWT verify(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(jwsVerifier);
        Date exp = (isRefresh)
                ? (new Date(signedJWT.getJWTClaimsSet().getExpirationTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                : signedJWT.getJWTClaimsSet().getExpirationTime();
        if(!(verified && exp.after(new Date()))) throw new RuntimeException("Token unauthenticated.");
        if(tokenExpRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) throw new RuntimeException("token expiry time");

        return signedJWT;
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        boolean isValid = true;
        try{
            verify(token, false);
        }catch(Exception e){
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

}
