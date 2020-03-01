package com.vacationplanner.util;

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

}
