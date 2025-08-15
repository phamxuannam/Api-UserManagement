package com.phxnnam.Api_UserManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "UserName", nullable = false)
    String username;

    @Column(name = "Password", nullable = false)
    String password;

    @Column(name = "FullName",nullable = false)
    String fullName;

    @Column(name = "Active")
    int isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_name"))
    Set<RoleEntity> roles = new HashSet<>();
}
