package com.java.challenge.repository;

import com.java.challenge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    Boolean existsByEmail(String email);
}
