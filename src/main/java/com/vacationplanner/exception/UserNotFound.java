package com.vacationplanner.exception;

public class UserNotFound extends VPException {

    public UserNotFound(String message) {
        super(message);
    }

    public UserNotFound() {
        super("The user requested was not found.");
    }
}
