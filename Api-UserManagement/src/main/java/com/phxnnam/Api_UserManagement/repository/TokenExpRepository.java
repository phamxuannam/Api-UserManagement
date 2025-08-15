package com.phxnnam.Api_UserManagement.repository;

import com.phxnnam.Api_UserManagement.entity.TokenExpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenExpRepository extends JpaRepository<TokenExpEntity, String> {
}
