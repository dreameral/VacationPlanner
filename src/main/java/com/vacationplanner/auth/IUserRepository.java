package com.vacationplanner.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
