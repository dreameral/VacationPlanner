package com.vacationplanner.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vacation")
@Data
public class Vacation extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User requestedBy;

  private Date startDate;

  private Date endDate;

  @Enumerated
  private VacationStatus status;
}
