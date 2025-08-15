package com.phxnnam.Api_UserManagement.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    String status;
    String messenger;
    T result;
}
