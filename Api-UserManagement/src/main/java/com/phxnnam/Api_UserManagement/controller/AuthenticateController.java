package com.phxnnam.Api_UserManagement.controller;

import com.nimbusds.jose.JOSEException;
import com.phxnnam.Api_UserManagement.dto.ApiResponse;
import com.phxnnam.Api_UserManagement.dto.request.AuthenticateRequest;
import com.phxnnam.Api_UserManagement.dto.request.IntrospectRequest;
import com.phxnnam.Api_UserManagement.dto.request.LogoutRequest;
import com.phxnnam.Api_UserManagement.dto.request.RefreshRequest;
import com.phxnnam.Api_UserManagement.dto.response.AuthenticateResponse;
import com.phxnnam.Api_UserManagement.dto.response.IntrospectResponse;
import com.phxnnam.Api_UserManagement.dto.response.RefreshResponse;
import com.phxnnam.Api_UserManagement.service.IAuthenticateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/auth")
public class AuthenticateController {

    IAuthenticateService authenticateService;

    @PostMapping("/login")
    ApiResponse<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ApiResponse.<AuthenticateResponse>builder()
                .status("200")
                .messenger("successful")
                .result(authenticateService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .status("200")
                .messenger("successful")
                .result(authenticateService.introspect(request))
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<RefreshResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        return ApiResponse.<RefreshResponse>builder()
                .status("200")
                .messenger("successful")
                .result(authenticateService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticateService.logout(request);
        return ApiResponse.<Void>builder()
                .status("200")
                .messenger("successful")
                .build();
    }

}
