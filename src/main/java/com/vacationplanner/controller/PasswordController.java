package com.vacationplanner.controller;

import com.vacationplanner.dto.ResetPasswordDTO;
import com.vacationplanner.dto.Success;
import com.vacationplanner.model.User;
import com.vacationplanner.service.IEmailService;
import com.vacationplanner.service.ISecurityService;
import com.vacationplanner.service.IUserService;
import com.vacationplanner.util.ConstantVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PasswordController {
  @Autowired
  private IUserService userService;

  @Autowired
  private ISecurityService securityService;

  @Autowired
  private IEmailService emailService;

  @GetMapping(value = "/forgotPassword")
  public Success forgotPassword(@RequestParam(value = "email") String email) {
    User user = userService.findByEmail(email);

    if (user == null)
      return new Success(false); //TODO handle error

    String randomToken = UUID.randomUUID().toString();
    user.setResetToken(randomToken);
    userService.save(user);

    SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
    passwordResetEmail.setTo(user.getEmail());
    passwordResetEmail.setSubject("Password Reset Request");
    passwordResetEmail.setText("To reset password click the link below:\n" +
        ConstantVariables.APPLICATION_URL + "/resetPassword?token=" + randomToken);

    emailService.sendEmail(passwordResetEmail);

    return new Success(true);
  }

  @PostMapping(value = "/resetPassword")
  public Success resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
    User user = userService.findByResetToken(resetPasswordDTO.getResetToken());

    if (user == null)
      return new Success(false); //TODO handle error

    user.setPassword(resetPasswordDTO.getNewPassword());
    userService.save(user);
    securityService.autoLogin(user.getUsername(), resetPasswordDTO.getNewPassword());

    return new Success(true);
  }

}
