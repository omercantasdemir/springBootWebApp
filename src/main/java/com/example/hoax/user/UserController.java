package com.example.hoax.user;

import com.example.hoax.shared.GenericMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/v1/users")
    GenericMessage createUSer(@RequestBody User user) {
        userService.save(user);
        return new GenericMessage("User is created");
    }
}
