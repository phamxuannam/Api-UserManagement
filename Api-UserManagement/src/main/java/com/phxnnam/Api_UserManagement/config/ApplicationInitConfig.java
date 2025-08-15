package com.phxnnam.Api_UserManagement.config;

import com.phxnnam.Api_UserManagement.entity.RoleEntity;
import com.phxnnam.Api_UserManagement.entity.UserEntity;
import com.phxnnam.Api_UserManagement.repository.RoleRepository;
import com.phxnnam.Api_UserManagement.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){

                RoleEntity role = roleRepository.findById("ADMIN").orElseThrow(() -> new RuntimeException("Role doesn't exists."));
                Set<RoleEntity> setRole = new HashSet<>();
                setRole.add(role);

                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .fullName("Admin")
                        .roles(setRole)
                        .isActive(1)
                        .build();

                userRepository.save(user);
            }
        };
    }
}
