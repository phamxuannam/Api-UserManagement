package com.phxnnam.Api_UserManagement.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import com.phxnnam.Api_UserManagement.dto.request.AuthenticateRequest;
import com.phxnnam.Api_UserManagement.dto.request.IntrospectRequest;
import com.phxnnam.Api_UserManagement.dto.request.LogoutRequest;
import com.phxnnam.Api_UserManagement.dto.request.RefreshRequest;
import com.phxnnam.Api_UserManagement.dto.response.AuthenticateResponse;
import com.phxnnam.Api_UserManagement.dto.response.IntrospectResponse;
import com.phxnnam.Api_UserManagement.dto.response.RefreshResponse;
import com.phxnnam.Api_UserManagement.entity.UserEntity;

import java.text.ParseException;

public interface IAuthenticateService {
    AuthenticateResponse authenticate(AuthenticateRequest request);
    String generateToken(UserEntity user);
    String getRole(String username);
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
    SignedJWT verify(String token, boolean isRefresh) throws JOSEException, ParseException;
    IntrospectResponse introspect(IntrospectRequest request);
}
