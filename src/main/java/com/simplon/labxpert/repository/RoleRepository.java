package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.Role;
import com.simplon.labxpert.model.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(EUserRole name);
}
