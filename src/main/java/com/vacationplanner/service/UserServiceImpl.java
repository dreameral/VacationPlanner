package com.vacationplanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vacationplanner.model.User;
import com.vacationplanner.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {
  private final IUserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(IUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public void save(User user) {
    Optional<User> entity = null;
    if (user.getId() != null)
      entity = userRepository.findById(user.getId());

    if (entity != null && entity.isPresent()) {
      User updatedUser = entity.get();

      if (user.getPassword() != null)
        updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      if (user.getRole() != null)
        updatedUser.setRole(user.getRole());
      if (user.getUsername() != null)
        updatedUser.setUsername(user.getUsername());

      userRepository.save(updatedUser);
    } else {
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

      userRepository.save(user);
    }
  }

  @Override
  public User findById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.isPresent() ? user.get() : null;
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User findByEmailOrUsername(String email, String username) {
    return userRepository.findByEmailOrUsername(email, username);
  }

  @Override
  public User findByResetToken(String resetToken) {
    return userRepository.findByResetToken(resetToken);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }
}
