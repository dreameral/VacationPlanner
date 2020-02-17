package com.vacationplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacationplanner.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
