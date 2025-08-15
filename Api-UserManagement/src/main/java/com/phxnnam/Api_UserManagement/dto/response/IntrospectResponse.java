package com.phxnnam.Api_UserManagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class IntrospectResponse {
    boolean valid;
}
