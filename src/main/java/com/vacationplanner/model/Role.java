package com.vacationplanner.model;

public enum Role {
  ADMIN(0), TEAM_LEADER(1), TEAM_MEMBER(2);

  private int id;

  Role(int id) {
    this.id = id;
  }
}
