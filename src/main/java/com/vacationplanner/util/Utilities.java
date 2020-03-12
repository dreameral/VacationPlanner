package com.vacationplanner.util;

import com.vacationplanner.model.VacationStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

  public static String getLoggedInUsername() {
    return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
  }

  public static Date getDateFromString(String date) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantVariables.DATE_FORMAT);
    return dateFormat.parse(date);
  }

  public static VacationStatus getVacationStatusFromString(String status) {
    if (status.equalsIgnoreCase("pending"))
      return VacationStatus.PENDING;
    else if (status.equalsIgnoreCase("approved"))
      return VacationStatus.APPROVED;
    else if (status.equalsIgnoreCase("rejected"))
      return VacationStatus.REJECTED;

    return VacationStatus.UNKNOWN;
  }

}
