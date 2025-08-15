package com.phxnnam.Api_UserManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String username;
    String password;
    String fullName;
    Set<RoleResponse> roles;
}
