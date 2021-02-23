package com.vacationplanner.exception;

public class InvalidTokenException extends VPException {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException() {
        super("Invalid Token Provided.");
    }

}
