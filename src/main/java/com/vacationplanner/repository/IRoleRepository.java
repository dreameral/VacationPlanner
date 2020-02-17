package com.vacationplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vacationplanner.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
