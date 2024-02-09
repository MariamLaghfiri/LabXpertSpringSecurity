package com.simplon.labxpert.repository;

import com.simplon.labxpert.model.entity.User;
import com.simplon.labxpert.model.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Repository for the User entity.
 * It contains all the methods that we need to interact with the User table in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameAndUserIDNot(String username, Long id);
    Optional<User> findByEmailAndUserIDNot(String email, Long id);
}
