package com.example.hoax;

import com.example.hoax.user.User;
import com.example.hoax.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HoaxCreatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoaxCreatorApplication.class, args);
    }

    @Bean
    CommandLineRunner createData(UserRepository userRepository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return (args) -> {
            for (int i = 0; i < 25; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setMail("user" + i + "@mail.com");
                user.setPassword(passwordEncoder.encode("Passw*rd"));
                user.setActive(true);
                userRepository.save(user);

            }

        };
    }
}
