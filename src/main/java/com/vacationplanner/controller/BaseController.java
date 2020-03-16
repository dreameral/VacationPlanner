package com.vacationplanner.controller;

import com.vacationplanner.model.Role;
import com.vacationplanner.model.User;
import com.vacationplanner.service.*;
import com.vacationplanner.util.UserValidator;
import com.vacationplanner.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
	@Autowired
	protected IUserService userService;

	@Autowired
	protected VacationService vacationService;

	@Autowired
	protected IEmailService emailService;

	@Autowired
	protected ISecurityService securityService;

	@Autowired
	protected UserDetailsServiceImpl userDetailsService;

	@Autowired
	protected UserValidator userValidator;

	protected User getLoggedInUser() {
		return userService.findByUsername(Utilities.getLoggedInUsername());
	}

	protected boolean isAdmin(User user) {
		return user.getRole() == Role.ADMIN;
	}

	protected boolean isTeamLeader(User user) {
		return user.getRole() == Role.TEAM_LEADER;
	}

	protected boolean isTeamMember(User user) {
		return user.getRole() == Role.TEAM_MEMBER;
	}

}