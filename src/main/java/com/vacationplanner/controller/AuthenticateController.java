package com.vacationplanner.controller;

import com.vacationplanner.model.VerifyAccountDTO;
import com.vacationplanner.entity.Role;
import com.vacationplanner.entity.VerificationToken;
import com.vacationplanner.service.VerificationTokenService;
import com.vacationplanner.util.Constants;
import com.vacationplanner.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vacationplanner.model.LoginDTO;
import com.vacationplanner.model.Success;
import com.vacationplanner.entity.User;
import com.vacationplanner.util.JWTUtils;

import java.util.UUID;

@RestController
public class AuthenticateController extends BaseController {
	private final AuthenticationManager authenticationManager;
	private final VerificationTokenService verificationTokenService;

	@Autowired
	public AuthenticateController(AuthenticationManager authenticationManager, VerificationTokenService verificationTokenService) {
		this.authenticationManager = authenticationManager;
		this.verificationTokenService = verificationTokenService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody User user, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return ResponseEntity.ok(new Success(false));
		}

		String token = UUID.randomUUID().toString();
		String textMessage = "Thank you for signing up!\n\nTo start using your account you have to first verify it by clicking the link below:\n" +
				Constants.APPLICATION_URL + "/verifyAccount?token=" + token;
		emailService.sendEmail(Utilities.getMailMessage(new String[]{user.getEmail()}, "ACCOUNT VERIFICATION REQUIRED", textMessage));

		user.setRole(Role.ADMIN); // only administrators can register
		userService.save(user);

		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setUser(user);
		verificationToken.setToken(token);
		verificationToken.setExpirationDate(Utilities.calculateExpiryDate(60));

		verificationTokenService.save(verificationToken);


		return ResponseEntity.ok(new Success(true));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginDTO createAuthenticationToken(@RequestBody User user) throws Exception {
		user = userService.findByEmailOrUsername(user.getUsername(), user.getUsername());

		if (user == null || !user.isEnabled()) {
			throw new Exception("The user may not exist or it is not activated yet.");
		}

		final String token = JWTUtils.generateToken(user.getUsername());
		return new LoginDTO(token, user);
	}

	@PostMapping(value = "/verifyAccount")
	public User verifyAccount(@RequestBody VerifyAccountDTO verifyAccountDTO) throws Exception {
		VerificationToken verificationToken = verificationTokenService.findByToken(verifyAccountDTO.getToken());

		if (verificationToken == null || !verificationToken.isTokenValid())
			throw new Exception("The verification token does not exist or it has expired. Please sign up again.");

		User user = verificationToken.getUser();
		user.setEnabled(true);

		userService.save(user);

		return user;
	}
}
