package com.phxnnam.Api_UserManagement.controller;

import com.phxnnam.Api_UserManagement.dto.ApiResponse;
import com.phxnnam.Api_UserManagement.dto.request.RoleAssignRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserUpdateRequest;
import com.phxnnam.Api_UserManagement.dto.response.UserResponse;
import com.phxnnam.Api_UserManagement.dto.response.UserUpdateResponse;
import com.phxnnam.Api_UserManagement.entity.UserEntity;
import com.phxnnam.Api_UserManagement.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    IUserService userService;

    @PostMapping
    ApiResponse<UserResponse> create(@RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .status("200")
                .messenger("create user successful")
                .result(userService.create(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .status("200")
                .messenger("Successful.")
                .result(userService.getById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .status("200")
                .messenger("Successful. ")
                .result(userService.getAll())
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInFo(){
        return ApiResponse.<UserResponse>builder()
                .status("200")
                .messenger("")
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/updateInfo/{id}")
    ApiResponse<UserResponse> updateMyInfo(@PathVariable String id, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .status("200")
                .messenger("")
                .result(userService.updateMyInfo(id, request))
                .build();
    }

    @PatchMapping("/updateRoleForUser/{id}")
    ApiResponse<String> updateRoleForUser(@PathVariable String id, @RequestBody RoleAssignRequest request){
        return ApiResponse.<String>builder()
                .status("200")
                .messenger("")
                .result(userService.updateRoleForUser(id, request))
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<String> turnOnOff(@PathVariable String id){
        return ApiResponse.<String>builder()
                .status("200")
                .messenger("")
                .result(userService.turnOnOffById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .status("200")
                .messenger("")
                .result(userService.deleteById(id))
                .build();
    }

}
