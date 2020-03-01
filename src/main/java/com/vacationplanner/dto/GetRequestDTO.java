package com.vacationplanner.dto;

import com.vacationplanner.model.Request;
import com.vacationplanner.model.RequestDay;
import com.vacationplanner.model.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GetRequestDTO {
  private Long id;
  private Set<String> days;
  private Long userId;
  private RequestStatus status;

  public GetRequestDTO(Request request, List<RequestDay> days) {
    this.id = request.getId();
    this.days = convertToString(days);
    this.userId = request.getUser().getId();
    this.status = request.getStatus();
  }

  private static Set<String> convertToString(List<RequestDay> days) {
    Set<String> retVal = new HashSet<>();

    for (RequestDay requestDay : days) {
      retVal.add(requestDay.getDay().toString());
    }

    return retVal;
  }
}
