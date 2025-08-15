package com.phxnnam.Api_UserManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    String password;
    String fullName;
}
