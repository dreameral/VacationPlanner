package com.vacationplanner.dto;

import com.vacationplanner.model.Vacation;
import com.vacationplanner.model.VacationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class GetVacationDTO {
  private Long id;
  private Date startDate;
  private Date endDate;
  private Long userId;
  private VacationStatus status;

  public GetVacationDTO(Vacation vacation) {
    this.id = vacation.getId();
    this.startDate = vacation.getStartDate();
    this.endDate = vacation.getEndDate();
    this.userId = vacation.getUser().getId();
    this.status = vacation.getStatus();
  }

}
