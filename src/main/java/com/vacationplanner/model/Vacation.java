package com.vacationplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vacation")
@Getter
@Setter
public class Vacation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  private Date startDate;

  private Date endDate;

  @Enumerated
  private VacationStatus status;
}