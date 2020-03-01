package com.vacationplanner.controller;

import com.vacationplanner.model.Role;
import com.vacationplanner.model.User;
import com.vacationplanner.service.RequestDayService;
import com.vacationplanner.service.RequestService;
import com.vacationplanner.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;

import com.vacationplanner.service.IUserService;

public class RestAPIController {
	@Autowired
	protected IUserService userService;

	@Autowired
	protected RequestService requestService;

	@Autowired
	protected RequestDayService requestDayService;

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