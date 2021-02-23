package com.vacationplanner.service;

import java.util.Optional;
import java.util.UUID;

import com.vacationplanner.entity.Role;
import com.vacationplanner.entity.VerificationToken;
import com.vacationplanner.exception.InvalidTokenException;
import com.vacationplanner.exception.UserNotFound;
import com.vacationplanner.model.Success;
import com.vacationplanner.util.Constants;
import com.vacationplanner.util.VPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vacationplanner.entity.User;
import com.vacationplanner.repository.UserRepository;

@Service
public class UserService extends BasicService<User, UserRepository> {
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       VerificationTokenService verificationTokenService) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationTokenService = verificationTokenService;
    }

    public User save(User user) {
        Optional<User> entity = Optional.empty();
        if (user.getId() != null)
            entity = repository.findById(user.getId());

        if (entity.isPresent()) {
            User updatedUser = entity.get();

            if (user.getPassword() != null)
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRole() != null)
                updatedUser.setRole(user.getRole());
            if (user.getUsername() != null)
                updatedUser.setUsername(user.getUsername());

            return repository.save(updatedUser);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        }
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void resetPassword(String resetToken, String newPassword) throws InvalidTokenException {
        User user = repository.findByResetToken(resetToken);
        if (user == null) {
            throw new InvalidTokenException();
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken("");
        repository.save(user);
    }

    public void emailToResetPassword(String email) {
        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UserNotFound();
        }

        String randomToken = UUID.randomUUID().toString();
        user.setResetToken(randomToken);
        repository.save(user);

        String emailContent = String.format(Constants.RESET_PASSWORD_MESSAGE, Constants.APPLICATION_URL, randomToken);

        emailService.sendEmail(VPUtils.getMailMessage(new String[]{user.getEmail()}, Constants.RESET_PASSWORD_SUBJECT, emailContent));
    }

    public void activateUser(String verificationToken) {
        VerificationToken token = verificationTokenService.findByToken(verificationToken);

        if (token == null || !token.isTokenValid()) {
            throw new InvalidTokenException("The verification token does not exist or it has expired. Please sign up again.");
        }

        User user = token.getUser();
        user.setEnabled(true);

        repository.save(user);
    }

    public User registerUser(User user) {
        String token = UUID.randomUUID().toString();
        String textMessage = String.format(Constants.REGISTRATION_MESSAGE, Constants.APPLICATION_URL, token);
        emailService.sendEmail(VPUtils.getMailMessage(new String[]{user.getEmail()}, Constants.REGISTRATION_SUBJECT, textMessage));

        user.setRole(Role.ADMIN); // only administrators can register
        repository.save(user);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpirationDate(VPUtils.calculateExpiryDate(60));

        verificationTokenService.save(verificationToken);
        return repository.getOne(user.getId());
    }

}
