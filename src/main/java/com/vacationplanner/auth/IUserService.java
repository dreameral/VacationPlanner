package com.vacationplanner.auth;

public interface IUserService {
    void save(User user);

    User findByUsername(String username);
}
