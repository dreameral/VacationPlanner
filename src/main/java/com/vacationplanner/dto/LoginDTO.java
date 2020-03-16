package com.vacationplanner.dto;

import com.vacationplanner.model.User;
import lombok.Getter;

@Getter
public class LoginDTO {
	private final String jwtToken;
	private final User user;

	public LoginDTO(String jwtToken, User user) {
		this.jwtToken = jwtToken;
		this.user = user;
	}
}
