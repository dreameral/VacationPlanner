package com.vacationplanner.service;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {
  void sendEmail(SimpleMailMessage email);
}
