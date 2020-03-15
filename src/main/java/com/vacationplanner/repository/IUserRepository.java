package com.vacationplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacationplanner.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	User findByUsername(String username);

	User findByResetToken(String resetToken);
}
