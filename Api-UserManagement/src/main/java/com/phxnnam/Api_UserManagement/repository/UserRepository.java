package com.phxnnam.Api_UserManagement.repository;

import com.phxnnam.Api_UserManagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r where r.name = :roleName")
    List<UserEntity> findByRoleName(@Param("roleName")String roleName);
}
