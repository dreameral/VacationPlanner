package com.vacationplanner.controller;

import com.vacationplanner.dto.GetVacationDTO;
import com.vacationplanner.dto.PostVacationDTO;
import com.vacationplanner.dto.Success;
import com.vacationplanner.model.*;
import com.vacationplanner.util.ConstantVariables;
import com.vacationplanner.util.Utilities;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class VacationController extends RestAPIController {

  @GetMapping(value = "/vacation")
  public List<GetVacationDTO> listAllLeavesVacations(@RequestParam(value = "userId", required = false) Long userId,
                                                     @RequestParam(value = "status", required = false) String status) {
    if (userId != null && status != null) {
      return getByUserAndStatus(userId, status);
    } else if (userId != null) {
      return getByUserId(userId);
    } else if (status != null) {
      return getByStatus(status);
    }

    List<GetVacationDTO> retVal = new ArrayList<>();
    for (Vacation vacation : vacationService.findAll()) {
      retVal.add(new GetVacationDTO(vacation));
    }

    return retVal;
  }

  @GetMapping(value = "/vacation/{id}")
  public GetVacationDTO getById(@PathVariable("id") Long id) {
    Vacation vacation = vacationService.getById(id);

    if (vacation == null)
      return null;

    return new GetVacationDTO(vacation);
  }

  @PostMapping(value = "/vacation")
  public ResponseEntity<?> createVacation(@RequestBody PostVacationDTO leaveDTO) throws ParseException {
    // populate the request
    Vacation vacation = new Vacation();
    vacation.setStatus(VacationStatus.PENDING); // every request will have the pending status when created
    vacation.setUser(userService.findByUsername(Utilities.getLoggedInUsername()));
    vacation.setStartDate(Utilities.getDateFromString(leaveDTO.getStartDate()));
    vacation.setEndDate(Utilities.getDateFromString(leaveDTO.getEndDate()));

    vacationService.save(vacation);

    return ResponseEntity.ok(new Success(true));
  }

  @PutMapping(value = "/vacation/{id}")
  public ResponseEntity<?> updateVacation(@PathVariable("id") Long id, @RequestBody PostVacationDTO leaveDTO) throws ParseException {
    Vacation vacation = vacationService.getById(id);

    if (leaveDTO.getStatus() != null)
      vacation.setStatus(Utilities.getVacationStatusFromString(leaveDTO.getStatus()));

    if (leaveDTO.getStartDate() != null)
      vacation.setStartDate(Utilities.getDateFromString(leaveDTO.getStartDate()));

    if (leaveDTO.getEndDate() != null)
      vacation.setEndDate(Utilities.getDateFromString(leaveDTO.getEndDate()));

    vacationService.save(vacation);

    return ResponseEntity.ok(new Success(true));
  }

  @DeleteMapping(value = "/vacation/{id}")
  public void deleteVacation(@PathVariable("id") Long id) {
    Vacation vacation = vacationService.getById(id);

    if (vacation == null)
      return;

    User loggedInUser = getLoggedInUser();
    if (!isAdmin(loggedInUser) && !loggedInUser.equals(vacation.getUser())) {
      throw new AccessDeniedException(ConstantVariables.NOT_ALLOWED);
    }

    vacationService.deleteById(id);
  }

  private List<GetVacationDTO> getByUserId(Long userId) {
    List<GetVacationDTO> retVal = new ArrayList<>();

    User user = userService.findById(userId);

    if (user == null)
      return retVal;

    List<Vacation> vacations = vacationService.findByUser(user);

    for (Vacation vacation : vacations) {
      retVal.add(new GetVacationDTO(vacation));
    }

    return retVal;
  }

  private List<GetVacationDTO> getByStatus(String status) {
    List<GetVacationDTO> retVal = new ArrayList<>();

    VacationStatus vacationStatus = Utilities.getVacationStatusFromString(status);
    List<Vacation> vacations = vacationService.findByStatus(vacationStatus);

    for (Vacation vacation : vacations) {
      retVal.add(new GetVacationDTO(vacation));
    }

    return retVal;
  }

  private List<GetVacationDTO> getByUserAndStatus(Long userId, String status) {
    List<GetVacationDTO> retVal = new ArrayList<>();

    VacationStatus vacationStatus = Utilities.getVacationStatusFromString(status);
    User user = userService.findById(userId);

    if (user == null)
      return retVal;

    //TODO write a common method to get list of GetRequestDTOs from a Request
    List<Vacation> vacations = vacationService.findByUserAndStatus(user, vacationStatus);

    for (Vacation vacation : vacations) {
      retVal.add(new GetVacationDTO(vacation));
    }

    return retVal;
  }

}
