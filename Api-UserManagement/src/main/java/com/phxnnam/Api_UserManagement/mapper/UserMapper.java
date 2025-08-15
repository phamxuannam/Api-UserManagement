package com.phxnnam.Api_UserManagement.mapper;

import com.phxnnam.Api_UserManagement.dto.request.UserRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserUpdateRequest;
import com.phxnnam.Api_UserManagement.dto.response.UserResponse;
import com.phxnnam.Api_UserManagement.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles",ignore = true)
    UserEntity toUser(UserRequest request);

    UserResponse toUserResponse(UserEntity entity);

    void updateUser(@MappingTarget UserEntity user, UserUpdateRequest request);
}
