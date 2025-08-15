package com.phxnnam.Api_UserManagement.config;

import com.nimbusds.jose.JOSEException;
import com.phxnnam.Api_UserManagement.dto.request.IntrospectRequest;
import com.phxnnam.Api_UserManagement.service.IAuthenticateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomJwtDecodeConfig implements JwtDecoder {

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    NimbusJwtDecoder nimbusJwtDecoder;

    IAuthenticateService authenticateService;

    @Override
    public Jwt decode(String token) throws JwtException {
        var response = authenticateService.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
        if(!response.isValid()) throw new JwtException("Token unauthenticated.");

        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(),"HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}
