package com.vacationplanner.service;

import com.vacationplanner.model.Vacation;
import com.vacationplanner.model.VacationStatus;
import com.vacationplanner.model.User;
import com.vacationplanner.repository.IVacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationService {

  @Autowired
  IVacationRepository leaveRepository;

  public List<Vacation> findByUser(User user) {
    return leaveRepository.findByUser(user);
  }

  public List<Vacation> findByUserAndStatus(User user, VacationStatus status) {
    return leaveRepository.findByUserAndStatus(user, status);
  }

  public List<Vacation> findByStatus(VacationStatus status) {
    return leaveRepository.findByStatus(status);
  }

  public void save(Vacation vacation) {
    leaveRepository.save(vacation);
  }

  public List<Vacation> findAll() {
    return leaveRepository.findAll();
  }

  public Vacation getById(Long id) {
    Optional<Vacation> request = leaveRepository.findById(id);
    if (request.isPresent())
      return request.get();

    return null;
  }

  public void deleteById(Long id) {
    leaveRepository.deleteById(id);
  }

}
