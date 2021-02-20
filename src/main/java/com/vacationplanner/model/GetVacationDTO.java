package com.vacationplanner.model;

import com.vacationplanner.entity.Vacation;
import com.vacationplanner.entity.VacationStatus;
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
        this.userId = vacation.getRequestedBy().getId();
        this.status = vacation.getStatus();
    }

}
