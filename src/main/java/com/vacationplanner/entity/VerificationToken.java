package com.vacationplanner.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verification_token")
@Data
public class VerificationToken extends BasicEntity {

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
