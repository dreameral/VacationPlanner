package com.vacationplanner.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostVacationDTO {
  private String startDate;
  private String endDate;
  private String status;
  private String message;
  private boolean sendEmail;

  public PostVacationDTO() {
    super();
    sendEmail = true;
  }

}
