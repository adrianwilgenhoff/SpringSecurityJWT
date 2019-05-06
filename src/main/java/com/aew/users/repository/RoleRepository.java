package com.aew.users.repository;

import java.util.Optional;

import com.aew.users.domain.Authority;
import com.aew.users.domain.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository <Authority, String> {
    Optional <Authority> findByName(RoleName roleName);
}