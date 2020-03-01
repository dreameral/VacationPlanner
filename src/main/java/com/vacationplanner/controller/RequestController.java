package com.vacationplanner.controller;

import com.vacationplanner.dto.GetRequestDTO;
import com.vacationplanner.dto.PostRequestDTO;
import com.vacationplanner.model.*;
import com.vacationplanner.util.ConstantVariables;
import com.vacationplanner.util.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class RequestController extends RestAPIController {

  @GetMapping(value = "/request")
  public List<GetRequestDTO> listAllRequests(@RequestParam("userId") Long userId) {
    if (userId != null)
      return getByUserId(userId);

    List<GetRequestDTO> retVal = new ArrayList<>();
    for (Request request : requestService.findAll()) {
      retVal.add(new GetRequestDTO(request, requestDayService.findByRequestId(request.getId())));
    }

    return retVal;
  }

  public List<GetRequestDTO> getByUserId(@RequestParam("userId") Long userId) {
    List<GetRequestDTO> retVal = new ArrayList<>();

    User user = userService.findById(userId);

    if (user == null)
      return retVal;

    List<Request> requests = requestService.findByUser(user);

    for (Request request : requests) {
      List<RequestDay> requestDays = requestDayService.findByRequestId(request.getId());
      retVal.add(new GetRequestDTO(request, requestDays));
    }

    return retVal;
  }

  @GetMapping(value = "/request/{id}")
  public GetRequestDTO getById(@PathVariable("id") Long id) {
    Request request = requestService.getById(id);

    if (request == null)
      return null;

    List<RequestDay> requestDays = requestDayService.findByRequestId(id);

    return new GetRequestDTO(request, requestDays);
  }

  @PostMapping(value = "/request")
  public ResponseEntity<?> createRequest(@RequestBody PostRequestDTO requestDTO) throws ParseException {
    Set<Date> requestedDays = requestDTO.getRequestedDays();
    // populate the request
    Request request = new Request();
    request.setStatus(RequestStatus.PENDING); // every request will have the pending status when created
    request.setUser(userService.findByUsername(Utilities.getLoggedInUsername()));

    requestService.save(request);

    // populate the requestDay
    RequestDay requestDay;
    for (Date date : requestedDays) {
      requestDay = new RequestDay();
      requestDay.setRequestId(request.getId());
      requestDay.setDay(date);

      requestDayService.save(requestDay);
    }

    return ResponseEntity.ok(new Success(true));
  }

  @PutMapping(value = "/request/{id}")
  public ResponseEntity<?> updateRequest(@PathVariable("id") Long id, @RequestBody PostRequestDTO requestDTO) throws ParseException {
    if (requestDTO.getStatus() != null) {
      Request request = requestService.getById(id);
      request.setStatus(requestDTO.getStatus());
    }

    Set<Date> daysToUpdate = requestDTO.getRequestedDays();
    if (daysToUpdate.size() > 0) {
      requestDayService.deleteByRequestId(id);

      RequestDay requestDay;
      for (Date date : daysToUpdate) {
        requestDay = new RequestDay();
        requestDay.setRequestId(id);
        requestDay.setDay(date);

        requestDayService.save(requestDay);
      }
    }

    return ResponseEntity.ok(new Success(true));
  }

  @DeleteMapping(value = "/request/{id}")
  public void deleteRequest(@PathVariable("id") Long id) {
    Request request = requestService.getById(id);

    if (request == null)
      return;

    User loggedInUser = getLoggedInUser();
    if (!isAdmin(loggedInUser) && !loggedInUser.equals(request.getUser())) {
      throw new AccessDeniedException(ConstantVariables.NOT_ALLOWED);
    }

    requestDayService.deleteByRequestId(id);
    requestService.deleteById(id);
  }

}
