package com.phxnnam.Api_UserManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleEntity {
    @Id
    @Column(name = "RoleName")
    String name;

    @Column(name = "Description", nullable = false)
    String des;

    @Column(name = "Active")
    int isActive;

    @ManyToMany(mappedBy = "roles")
    Set<UserEntity> users = new HashSet<>();
}
