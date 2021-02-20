package com.vacationplanner.repository;

import com.vacationplanner.entity.Vacation;
import com.vacationplanner.entity.VacationStatus;
import com.vacationplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findByRequestedBy(User user);

    List<Vacation> findByRequestedByAndStatus(User user, VacationStatus status);

    List<Vacation> findByStatus(VacationStatus status);

}
