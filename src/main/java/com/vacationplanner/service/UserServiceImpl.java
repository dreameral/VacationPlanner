package com.vacationplanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vacationplanner.model.Role;
import com.vacationplanner.model.User;
import com.vacationplanner.repository.IRoleRepository;
import com.vacationplanner.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		Optional<User> entity = userRepository.findById(user.getId());
		Role userRole = getRole(user);

		if (entity.isPresent()) {
			User updatedUser = entity.get();

			if (user.getPassword() != null)
				updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			if (userRole != null)
				updatedUser.setRole(userRole);
			if (user.getUsername() != null)
				updatedUser.setUsername(user.getUsername());

			userRepository.save(updatedUser);
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			if (userRole != null)
				user.setRole(userRole);

			userRepository.save(user);
		}
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	private Role getRole(User user) {
		if (user.getRole() != null && user.getRole().getId() != null) {
			Optional<Role> retVal = roleRepository.findById(user.getRole().getId());

			if (retVal.isPresent())
				return retVal.get();
		}

		return null;
	}
}
