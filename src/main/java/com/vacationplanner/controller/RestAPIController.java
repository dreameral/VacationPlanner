package com.vacationplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vacationplanner.model.User;
import com.vacationplanner.service.IUserService;

@RestController
@RequestMapping("/rest/api/v1")
public class RestAPIController {
	@Autowired
	private IUserService userService;

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> findAll() {
		return userService.findAll();
	}

}
