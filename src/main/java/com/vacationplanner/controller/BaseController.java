package com.vacationplanner.controller;

import com.vacationplanner.entity.Role;
import com.vacationplanner.entity.User;
import com.vacationplanner.service.*;
import com.vacationplanner.util.UserValidator;
import com.vacationplanner.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected UserService userService;

    @Autowired
    protected VacationService vacationService;

    @Autowired
    protected EmailService emailService;

    @Autowired
    protected SecurityService securityService;

    @Autowired
    protected VPUserDetailsService userDetailsService;

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