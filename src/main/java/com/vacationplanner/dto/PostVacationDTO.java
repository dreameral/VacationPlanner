package com.vacationplanner.dto;

import com.vacationplanner.model.VacationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostVacationDTO {
  private String startDate;
  private String endDate;
  private String status;

}