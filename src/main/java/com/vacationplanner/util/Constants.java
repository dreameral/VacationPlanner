package com.vacationplanner.util;

public class Constants {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String APPLICATION_URL = "http://localhost:4200";

    public static final String AUTHENTICATION_HEADER_PREFIX = "Bearer ";
    public static final String DEFAULT_VACATION_MESSAGE = "Hi,\n\nI would like to take some days off the office. Can you please consider?";
    public static final String RESET_PASSWORD_MESSAGE = "To reset password click the link below: %s/resetPassword?token=%s";
    public static final String RESET_PASSWORD_SUBJECT = "Password Reset Request";
    public static final String REGISTRATION_MESSAGE = "Thank you for signing up!\n\nTo start using your account you have to first verify it by clicking the link below:\n %s/verifyAccount?token=%s";
    public static final String REGISTRATION_SUBJECT = "ACCOUNT VERIFICATION REQUIRED";

    // error messages
    public static final String NOT_ALLOWED = "You don't have the right permission for this operation.";
}
