package com.vacationplanner.model;

import lombok.Getter;

@Getter
public class JWTResponse {

	private final String jwttoken;

	public JWTResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
}
