package com.vacationplanner.repository;

import com.vacationplanner.model.Request;
import com.vacationplanner.model.RequestStatus;
import com.vacationplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRequestRepository extends JpaRepository<Request, Long> {

  List<Request> findByUser(User user);

  List<Request> findByUserAndStatus(User user, RequestStatus status);

  List<Request> findByStatus(RequestStatus status);

}
