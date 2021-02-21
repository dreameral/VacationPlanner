package com.vacationplanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vacationplanner.entity.User;
import com.vacationplanner.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        Optional<User> entity = null;
        if (user.getId() != null)
            entity = userRepository.findById(user.getId());

        if (entity != null && entity.isPresent()) {
            User updatedUser = entity.get();

            if (user.getPassword() != null)
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRole() != null)
                updatedUser.setRole(user.getRole());
            if (user.getUsername() != null)
                updatedUser.setUsername(user.getUsername());

            userRepository.save(updatedUser);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
        }
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmailOrUsername(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username);
    }

    public User findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
