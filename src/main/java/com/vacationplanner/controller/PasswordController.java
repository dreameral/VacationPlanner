package com.vacationplanner.controller;

import com.vacationplanner.model.ResetPasswordDTO;
import com.vacationplanner.model.Success;
import com.vacationplanner.entity.User;
import com.vacationplanner.util.Constants;
import com.vacationplanner.util.Utilities;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PasswordController extends BaseController {

  @GetMapping(value = "/forgotPassword")
  public Success forgotPassword(@RequestParam(value = "email") String email) {
    User user = userService.findByEmail(email);

    if (user == null)
      return new Success(false); //TODO handle error

    String randomToken = UUID.randomUUID().toString();
    user.setResetToken(randomToken);
    userService.save(user);

    String emailContent = "To reset password click the link below:\n" +
        Constants.APPLICATION_URL + "/resetPassword?token=" + randomToken;

    emailService.sendEmail(Utilities.getMailMessage(new String[]{user.getEmail()}, "Password Reset Request", emailContent));

    return new Success(true);
  }

  @PostMapping(value = "/resetPassword")
  public Success resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
    User user = userService.findByResetToken(resetPasswordDTO.getResetToken());

    if (user == null)
      return new Success(false); //TODO handle error

    user.setPassword(resetPasswordDTO.getNewPassword());
    user.setResetToken("");
    userService.save(user);
    securityService.autoLogin(user.getUsername(), resetPasswordDTO.getNewPassword());

    return new Success(true);
  }

}
