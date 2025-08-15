package com.phxnnam.Api_UserManagement.controller;

import com.phxnnam.Api_UserManagement.dto.ApiResponse;
import com.phxnnam.Api_UserManagement.dto.request.RoleRequest;
import com.phxnnam.Api_UserManagement.dto.response.RoleResponse;
import com.phxnnam.Api_UserManagement.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    IRoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .status("200")
                .messenger("successful")
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .status("200")
                .messenger("successful")
                .result(roleService.getAll())
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<String> turnOnOffById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .status("200")
                .messenger("successful")
                .result(roleService.turnOnOffById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .status("200")
                .messenger("successful")
                .result(roleService.deleteById(id))
                .build();
    }
}
