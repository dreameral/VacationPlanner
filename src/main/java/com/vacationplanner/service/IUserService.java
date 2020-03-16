package com.vacationplanner.service;

import java.util.List;

import com.vacationplanner.model.User;

public interface IUserService {
	void save(User user);

	User findById(Long id);

	User findByEmail(String email);

	User findByUsername(String username);

	User findByEmailOrUsername(String email, String username);

	User findByResetToken(String resetToken);

	List<User> findAll();

	void deleteById(Long id);
}
