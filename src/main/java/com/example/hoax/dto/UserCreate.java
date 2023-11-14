package com.example.hoax.dto;

import com.example.hoax.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(@NotBlank
                         @Size(min = 4, max = 255)
                         String username,
                         @NotBlank
                         @Email
                         String mail,
                         @Size(min = 8, max = 255)
                         @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
                         String password) {
    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setMail(mail);
        user.setPassword(password);
        return user;
    }
}
