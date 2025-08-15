package com.phxnnam.Api_UserManagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    String username;
    String password;
    String fullName;
}
