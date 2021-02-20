package com.vacationplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacationplanner.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String username);

    User findByResetToken(String resetToken);
}
