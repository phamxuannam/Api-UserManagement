package com.phxnnam.Api_UserManagement.mapper;

import com.phxnnam.Api_UserManagement.dto.request.RoleRequest;
import com.phxnnam.Api_UserManagement.dto.response.RoleResponse;
import com.phxnnam.Api_UserManagement.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper{

    @Mapping(target = "users", ignore = true)
    RoleEntity toRole(RoleRequest request);

    RoleResponse toRoleResponse(RoleEntity entity);
}
