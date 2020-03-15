package com.vacationplanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
  private String resetToken;
  private String newPassword;
}
