package com.phxnnam.Api_UserManagement.repository;

import com.phxnnam.Api_UserManagement.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    boolean existsByName(String name);

}
