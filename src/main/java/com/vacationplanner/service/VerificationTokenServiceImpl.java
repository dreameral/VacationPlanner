package com.vacationplanner.service;

import com.vacationplanner.model.VerificationToken;
import com.vacationplanner.repository.IVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements IVerificationTokenService {

  private final IVerificationTokenRepository verificationTokenRepository;

  @Autowired
  public VerificationTokenServiceImpl(IVerificationTokenRepository verificationTokenRepository) {
    this.verificationTokenRepository = verificationTokenRepository;
  }

  @Override
  public VerificationToken findByToken(String token) {
    return verificationTokenRepository.findByToken(token);
  }

  @Override
  public void save(VerificationToken verificationToken) {
    verificationTokenRepository.save(verificationToken);
  }
}
