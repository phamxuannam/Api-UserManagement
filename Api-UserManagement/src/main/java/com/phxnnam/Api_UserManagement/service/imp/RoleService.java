package com.phxnnam.Api_UserManagement.service.imp;

import com.phxnnam.Api_UserManagement.dto.request.RoleRequest;
import com.phxnnam.Api_UserManagement.dto.response.RoleResponse;
import com.phxnnam.Api_UserManagement.entity.RoleEntity;
import com.phxnnam.Api_UserManagement.entity.UserEntity;
import com.phxnnam.Api_UserManagement.mapper.RoleMapper;
import com.phxnnam.Api_UserManagement.mapper.UserMapper;
import com.phxnnam.Api_UserManagement.repository.RoleRepository;
import com.phxnnam.Api_UserManagement.repository.UserRepository;
import com.phxnnam.Api_UserManagement.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;
    UserRepository userRepository;
    UserMapper userMapper;


    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public RoleResponse create(RoleRequest request) {
        if(roleRepository.existsByName(request.getName()))
            throw new RuntimeException("Role already exists");
        RoleEntity entity = roleMapper.toRole(request);
        entity.setIsActive(1);
        roleRepository.save(entity);
        return roleMapper.toRoleResponse(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public String turnOnOffById(String id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(()-> new RuntimeException("RoleName doesn't exist."));
        List<UserEntity> listUser = userRepository.findByRoleName(id);

        if(role.getIsActive() > 0){
            role.setIsActive(0);
            for(UserEntity user : listUser){
                user.setIsActive(0);
                userRepository.save(user);
            }
            roleRepository.save(role);
            return "successful turn off";
        } else {
            role.setIsActive(1);
            for(UserEntity user : listUser){
                user.setIsActive(1);
                userRepository.save(user);
            }
            roleRepository.save(role);
            return "successful turn on";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public String deleteById(String id) {
        roleRepository.findById(id).orElseThrow( () -> new RuntimeException("Role doesn't exist. RoleService 56"));
        roleRepository.deleteById(id);
        return "Role deleted successfully";
    }
}
