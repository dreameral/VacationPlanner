package com.vacationplanner.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vacationplanner.model.User;
import com.vacationplanner.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(username);

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new HashSet<>());
	}
}
