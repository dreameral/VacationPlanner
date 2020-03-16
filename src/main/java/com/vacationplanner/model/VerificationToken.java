package com.vacationplanner.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private User user;

  private Date expirationDate;

  private String token;

  public boolean isTokenValid() {
    Date now = new Date();
    return expirationDate.after(now);
  }

}
