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
import java.util.*;

@RestController
@RequestMapping("/rest")
public class VacationController extends BaseController {

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
  public ResponseEntity<?> createVacation(@RequestBody PostVacationDTO vacationDTO) throws ParseException {
    // populate the request
    User user = getLoggedInUser();

    Vacation vacation = new Vacation();
    vacation.setStatus(VacationStatus.PENDING); // every request will have the pending status when created
    vacation.setRequestedBy(user);
    vacation.setStartDate(Utilities.getDateFromString(vacationDTO.getStartDate()));
    vacation.setEndDate(Utilities.getDateFromString(vacationDTO.getEndDate()));

    vacationService.save(vacation);

    if (vacationDTO.isSendEmail()) {
      User admin = user.getAdmin();
      User teamLeader = user.getTeamLeader();

      String[] to = new String[teamLeader == null ? 1 : 2];
      to[0] = admin.getEmail();
      if (teamLeader != null)
        to[1] = teamLeader.getEmail();

      String message = vacationDTO.getMessage() == null ? ConstantVariables.DEFAULT_VACATION_MESSAGE : vacationDTO.getMessage();

      emailService.sendEmail(Utilities.getMailMessage(to, "REQUEST FOR DAYS OFF", message));
    }

    return ResponseEntity.ok(new Success(true));
  }

  @PutMapping(value = "/vacation/{id}")
  public ResponseEntity<?> updateVacation(@PathVariable("id") Long id, @RequestBody PostVacationDTO vacationDTO) throws ParseException {
    VacationChanges changes = new VacationChanges();
    User loggedInUser = getLoggedInUser();
    Vacation vacation = vacationService.getById(id);

    if (vacationDTO.getStatus() != null) {
      changes.addChange("Status", vacation.getStatus().toString(), vacationDTO.getStatus());
      vacation.setStatus(Utilities.getVacationStatusFromString(vacationDTO.getStatus()));
    }

    if (vacationDTO.getStartDate() != null) {
      changes.addChange("StartDate", vacation.getStartDate().toString(), vacationDTO.getStartDate());
      vacation.setStartDate(Utilities.getDateFromString(vacationDTO.getStartDate()));
    }

    if (vacationDTO.getEndDate() != null) {
      changes.addChange("EndDate", vacation.getEndDate().toString(), vacationDTO.getEndDate());
      vacation.setEndDate(Utilities.getDateFromString(vacationDTO.getEndDate()));
    }

    vacationService.save(vacation);

    // send email
    User requester = vacation.getRequestedBy();

    String[] sendTo = new String[2];
    sendTo[0] = requester.getEmail();
    if (loggedInUser.equals(requester.getAdmin()))
      sendTo[1] = requester.getAdmin().getEmail();
    else
      sendTo[1] = requester.getTeamLeader().getEmail();

    emailService.sendEmail(Utilities.getMailMessage(sendTo, "STATUS UPDATE", changes.toString()));

    return ResponseEntity.ok(new Success(true));
  }

  @DeleteMapping(value = "/vacation/{id}")
  public void deleteVacation(@PathVariable("id") Long id) {
    Vacation vacation = vacationService.getById(id);

    if (vacation == null)
      return;

    User loggedInUser = getLoggedInUser();
    if (!isAdmin(loggedInUser) && !loggedInUser.equals(vacation.getRequestedBy())) {
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

  class VacationChanges {
    private HashMap<String, String[]> changes;

    public VacationChanges() {
      changes = new HashMap<>();
    }

    public void addChange(String field, String from, String to) {
      changes.put(field, new String[]{from, to});
    }

    @Override
    public String toString() {
      StringBuilder retVal = new StringBuilder();
      Iterator iterator = changes.entrySet().iterator();

      retVal.append("The changes made to your application for days off are:\n");
      while (iterator.hasNext()) {
        Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) iterator.next();

        retVal.append(entry.getKey()).append(" was changed from '")
            .append(entry.getValue()[0])
            .append("' to '")
            .append(entry.getValue()[1])
            .append("'.\n");
      }

      return retVal.toString();
    }
  }

}
