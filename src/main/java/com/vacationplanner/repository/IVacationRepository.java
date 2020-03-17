package com.vacationplanner.repository;

import com.vacationplanner.model.Vacation;
import com.vacationplanner.model.VacationStatus;
import com.vacationplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVacationRepository extends JpaRepository<Vacation, Long> {

  List<Vacation> findByRequestedBy(User user);

  List<Vacation> findByRequestedByAndStatus(User user, VacationStatus status);

  List<Vacation> findByStatus(VacationStatus status);

}
