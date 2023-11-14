package com.example.hoax.user;

import com.example.hoax.email.EmailService;
import com.example.hoax.user.exception.InvalidActivationTokenException;
import com.example.hoax.user.exception.NotUniqueMailException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;
    EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Transactional(rollbackOn = MailException.class)
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationMail(user.getMail(), user.getActivationToken());
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueMailException();
        }
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void activateUser(String token) {
        User inDB = userRepository.findByActivationToken(token);
        if (inDB == null) {
            throw new InvalidActivationTokenException();

        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);

    }

//    public User findUser(User user) {
//
//        return userRepository.findBy();
//
//
//    }
}
