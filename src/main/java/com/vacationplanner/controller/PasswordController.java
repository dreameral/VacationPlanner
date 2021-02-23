package com.vacationplanner.controller;

import com.vacationplanner.model.ResetPasswordDTO;
import com.vacationplanner.model.Success;
import com.vacationplanner.entity.User;
import com.vacationplanner.util.Constants;
import com.vacationplanner.util.VPUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PasswordController extends BaseController {

    @GetMapping(value = "/forgotPassword")
    public void forgotPassword(@RequestParam(value = "email") String email) {
        userService.emailToResetPassword(email);
    }

    @PostMapping(value = "/resetPassword")
    public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        userService.resetPassword(resetPasswordDTO.getResetToken(), resetPasswordDTO.getNewPassword());
    }

}
