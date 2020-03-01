package com.vacationplanner.repository;

import com.vacationplanner.model.RequestDay;
import com.vacationplanner.model.RequestDayId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRequestDayRepository extends JpaRepository<RequestDay, RequestDayId> {
  List<RequestDay> findByRequestId(Long requestId);

  void deleteByRequestId(Long requestId);
}
