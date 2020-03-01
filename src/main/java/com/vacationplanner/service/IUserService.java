package com.vacationplanner.service;

import java.util.List;

import com.vacationplanner.model.User;

public interface IUserService {
	void save(User user);

	User findById(Long id);

	User findByUsername(String username);

	List<User> findAll();

	void deleteById(Long id);
}
