package com.vacationplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vacationplanner.model.Success;
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

	@PostMapping(value = "/users")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		// TODO validate user

		userService.save(user);

		return ResponseEntity.ok(user);
	}

	@PutMapping(value = "/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		// TODO validate user

		user.setId(id);
		userService.save(user);

		return ResponseEntity.ok(user);
	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		userService.deleteById(id);

		return ResponseEntity.ok(new Success(true));
	}

}
