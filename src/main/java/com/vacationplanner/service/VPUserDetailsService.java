package com.vacationplanner.service;

import java.util.HashSet;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vacationplanner.entity.User;
import com.vacationplanner.repository.UserRepository;

@Service
public class VPUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	public VPUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

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
