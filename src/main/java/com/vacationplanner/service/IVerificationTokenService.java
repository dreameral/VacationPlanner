package com.vacationplanner.service;

import com.vacationplanner.model.VerificationToken;

public interface IVerificationTokenService {
  VerificationToken findByToken(String token);

  void save(VerificationToken verificationToken);
}
