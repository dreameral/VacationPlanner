package com.vacationplanner.controller;

import com.vacationplanner.model.VerifyAccountDTO;
import com.vacationplanner.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vacationplanner.entity.User;

@RestController
public class AuthenticateController extends BaseController {
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public AuthenticateController(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user) {
        //TODO this should accept a UserApiModel instead of a User entity
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(value = "/verifyAccount")
    public void verifyAccount(@RequestBody VerifyAccountDTO verifyAccountDTO) {
        userService.activateUser(verifyAccountDTO.getToken());
    }
}
