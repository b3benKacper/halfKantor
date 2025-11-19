package com.kantor.kantor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kantor.kantor.model.User;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
