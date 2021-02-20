package com.vacationplanner.service;

import com.vacationplanner.entity.Vacation;
import com.vacationplanner.entity.VacationStatus;
import com.vacationplanner.entity.User;
import com.vacationplanner.repository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationService {

  @Autowired
  VacationRepository leaveRepository;

  public List<Vacation> findByUser(User user) {
    return leaveRepository.findByRequestedBy(user);
  }

  public List<Vacation> findByUserAndStatus(User user, VacationStatus status) {
    return leaveRepository.findByRequestedByAndStatus(user, status);
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
