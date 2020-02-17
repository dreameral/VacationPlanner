package com.vacationplanner.service;

import com.vacationplanner.model.User;

public interface IUserService {
    void save(User user);

    User findByUsername(String username);
}
