package com.vacationplanner.repository;

import com.vacationplanner.model.Request;
import com.vacationplanner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRequestRepository extends JpaRepository<Request, Long> {

  List<Request> findByUser(User user);

}
