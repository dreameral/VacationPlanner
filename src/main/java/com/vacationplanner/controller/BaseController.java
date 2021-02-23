package com.vacationplanner.controller;

import com.vacationplanner.entity.Role;
import com.vacationplanner.entity.User;
import com.vacationplanner.service.*;
import com.vacationplanner.util.UserValidator;
import com.vacationplanner.util.VPUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected UserService userService;

    @Autowired
    protected VacationService vacationService;

    @Autowired
    protected EmailService emailService;

    protected User getLoggedInUser() {
        return userService.findByUsername(VPUtils.getLoggedInUsername());
    }

    protected boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    protected boolean isTeamMember(User user) {
        return user.getRole() == Role.TEAM_MEMBER;
    }

}