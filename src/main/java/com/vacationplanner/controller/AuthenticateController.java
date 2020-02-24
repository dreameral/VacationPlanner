package com.vacationplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vacationplanner.model.JWTResponse;
import com.vacationplanner.model.Success;
import com.vacationplanner.model.User;
import com.vacationplanner.service.ISecurityService;
import com.vacationplanner.service.IUserService;
import com.vacationplanner.service.UserDetailsServiceImpl;
import com.vacationplanner.util.JWTUtils;
import com.vacationplanner.util.UserValidator;

@RestController
public class AuthenticateController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody User user, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return ResponseEntity.ok(new Success(false));
		}

		String tempPassword = user.getPassword();

		userService.save(user);

		securityService.autoLogin(user.getUsername(), tempPassword);

		return ResponseEntity.ok(new Success(true));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {
		authenticate(user.getUsername(), user.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		final String token = JWTUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JWTResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
