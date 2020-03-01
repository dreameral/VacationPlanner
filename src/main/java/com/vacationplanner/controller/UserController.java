package com.vacationplanner.controller;

import com.vacationplanner.model.Request;
import com.vacationplanner.model.Success;
import com.vacationplanner.model.User;
import com.vacationplanner.util.ConstantVariables;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class UserController extends RestAPIController {

  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping(value = "/users/{id}")
  public User getById(@PathVariable("id") Long id) {
    return userService.findById(id);
  }

  @PostMapping(value = "/users")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    if (!isAdmin(getLoggedInUser())) {
      throw new AccessDeniedException(ConstantVariables.NOT_ALLOWED);
    }

    userService.save(user);

    return ResponseEntity.ok(user);
  }

  @PutMapping(value = "/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    user.setId(id);
    User loggedInUser = getLoggedInUser();

    if (isTeamMember(loggedInUser) && !user.equals(loggedInUser)) {
      throw new AccessDeniedException(ConstantVariables.NOT_ALLOWED);
    }

    userService.save(user);

    return ResponseEntity.ok(user);
  }

  @DeleteMapping(value = "/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
    if (!isAdmin(getLoggedInUser())) {
      throw new AccessDeniedException(ConstantVariables.NOT_ALLOWED);
    }

    userService.deleteById(id);

    return ResponseEntity.ok(new Success(true));
  }

}
