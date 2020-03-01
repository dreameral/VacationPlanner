package com.vacationplanner.dto;

import com.vacationplanner.model.RequestDay;
import com.vacationplanner.model.RequestStatus;
import com.vacationplanner.util.Utilities;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PostRequestDTO {
  private Set<String> days;
  private RequestStatus status;

  public Set<Date> getRequestedDays() throws ParseException {
    Set<Date> retVal = new HashSet<>();

    Date date;

    for (String day : days) {
      date = Utilities.getDateFromString(day);
      retVal.add(date);
    }

    return retVal;
  }
}
