package com.vacationplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "request_day")
@Getter
@Setter
@IdClass(RequestDayId.class)
public class RequestDay implements Serializable {
  @Id
  private Long requestId;

  @Id
  private Date day;
}
