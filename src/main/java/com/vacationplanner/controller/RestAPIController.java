package com.vacationplanner.controller;

import com.vacationplanner.model.Role;
import com.vacationplanner.model.User;
import com.vacationplanner.service.VacationService;
import com.vacationplanner.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;

import com.vacationplanner.service.IUserService;

public class RestAPIController {
	@Autowired
	protected IUserService userService;

	@Autowired
	protected VacationService vacationService;

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