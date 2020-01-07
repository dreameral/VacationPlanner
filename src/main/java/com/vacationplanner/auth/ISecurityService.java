package com.vacationplanner.auth;

public interface ISecurityService {
	String findLoggedInUsername();

    void autoLogin(String username, String password);
}
