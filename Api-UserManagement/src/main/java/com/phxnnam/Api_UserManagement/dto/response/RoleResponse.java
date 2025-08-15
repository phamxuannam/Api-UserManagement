package com.phxnnam.Api_UserManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    String name;
    String des;
}
