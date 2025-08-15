package com.phxnnam.Api_UserManagement.service.imp;

import com.phxnnam.Api_UserManagement.dto.request.RoleAssignRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserRequest;
import com.phxnnam.Api_UserManagement.dto.request.UserUpdateRequest;
import com.phxnnam.Api_UserManagement.dto.response.UserResponse;
import com.phxnnam.Api_UserManagement.entity.RoleEntity;
import com.phxnnam.Api_UserManagement.entity.UserEntity;
import com.phxnnam.Api_UserManagement.mapper.UserMapper;
import com.phxnnam.Api_UserManagement.repository.RoleRepository;
import com.phxnnam.Api_UserManagement.repository.UserRepository;
import com.phxnnam.Api_UserManagement.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {

        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("User already exists");
        UserEntity entity = userMapper.toUser(request);
        entity.setIsActive(1);

        RoleEntity roleE = RoleEntity.builder()
                .name("USER")
                .build();
        Set<RoleEntity> roleSet = new HashSet<>();
        roleSet.add(roleE);
        entity.setRoles(roleSet);

        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(entity);
        return userMapper.toUserResponse(entity);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @Override
    public List<UserResponse> getAll() {

        log.info("username: {}", SecurityContextHolder.getContext().getAuthentication().getName());
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(roles -> log.warn(roles.getAuthority()));

        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('STAFF')")
    @Override
    public UserResponse getById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()->new RuntimeException("Get error.User does not exist."));
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse getMyInfo(){
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
       UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Opp, Error."));
       return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse updateMyInfo(String id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exist"));
        userMapper.updateUser(user,request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public String updateRoleForUser(String id, RoleAssignRequest request) {
       UserEntity user = userRepository.findById(id)
               .orElseThrow(()->new RuntimeException("User doesn't exist.Place: UserService,72"));
       Set<RoleEntity> roleSet = new HashSet<>();
       RoleEntity role = roleRepository.findById(request.getRoles())
               .orElseThrow(() -> new RuntimeException("Role doesn't exist. Place: UserService,75"));
       if(role.getIsActive() < 1) throw new RuntimeException("Role doesn't Active");
       roleSet.add(role);
       user.setRoles(roleSet);
       userRepository.save(user);
       return "success";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public String turnOnOffById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Delete error.User does not exist"));
        if(user.getIsActive() == 1) {
            user.setIsActive(0);
            userRepository.save(user);
            return "Turned Off";
        } else {
            user.setIsActive(1);
            userRepository.save(user);
            return "Turned On";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public String deleteById(String id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Delete error.User does not exist"));
        userRepository.delete(user);
        return "Deleted";
    }

}
