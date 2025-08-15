package com.phxnnam.Api_UserManagement.service;

import com.phxnnam.Api_UserManagement.dto.request.RoleRequest;
import com.phxnnam.Api_UserManagement.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    String turnOnOffById(String id);
    String deleteById(String id);
}
