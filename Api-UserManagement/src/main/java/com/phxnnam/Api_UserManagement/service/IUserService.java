package com.phxnnam.Api_UserManagement.service;

import com.phxnnam.Api_UserManagement.dto.request.RoleAssignRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserUpdateRequest;
import com.phxnnam.Api_UserManagement.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse create(UserRequest request);
    List<UserResponse> getAll();
    UserResponse getById(String id);
    UserResponse getMyInfo();
    UserResponse updateMyInfo(String id, UserUpdateRequest request);
    String updateRoleForUser(String id, RoleAssignRequest request);
    String turnOnOffById(String id);
    String deleteById(String id);
}
