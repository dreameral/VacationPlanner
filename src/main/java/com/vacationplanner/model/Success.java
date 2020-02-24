package com.vacationplanner.model;

import lombok.Getter;

@Getter
public class Success {
	private final boolean success;

	public Success(boolean success) {
		this.success = success;
	}
}
