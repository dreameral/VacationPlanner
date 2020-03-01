package com.vacationplanner.model;

import java.io.Serializable;
import java.util.Date;

public class RequestDayId implements Serializable {
  private Long requestId;
  private Date day;

  public RequestDayId() {}

  public RequestDayId(Long requestId, Date day) {
    this.requestId = requestId;
    this.day = day;
  }
}
