package com.vacationplanner.service;

import com.vacationplanner.model.RequestDay;
import com.vacationplanner.repository.IRequestDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestDayService {

  @Autowired
  IRequestDayRepository requestDayRepository;

  public void save(RequestDay requestDay) {
    requestDayRepository.save(requestDay);
  }

  public List<RequestDay> findByRequestId(Long requestId) {
    return requestDayRepository.findByRequestId(requestId);
  }

  public void deleteByRequestId(Long requestId) {
    requestDayRepository.deleteByRequestId(requestId);
  }

}
