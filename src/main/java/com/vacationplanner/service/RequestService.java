package com.vacationplanner.service;

import com.vacationplanner.model.Request;
import com.vacationplanner.model.User;
import com.vacationplanner.repository.IRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

  @Autowired
  IRequestRepository requestRepository;

  public List<Request> findByUser(User user) {
    return requestRepository.findByUser(user);
  }

  public void save(Request request) {
    requestRepository.save(request);
  }

  public List<Request> findAll() {
    return requestRepository.findAll();
  }

  public Request getById(Long id) {
    Optional<Request> request = requestRepository.findById(id);
    if (request.isPresent())
      return request.get();

    return null;
  }

  public void deleteById(Long id) {
    requestRepository.deleteById(id);
  }

}
