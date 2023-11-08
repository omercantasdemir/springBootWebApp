package com.example.hoax.user;

import com.example.hoax.error.ApiError;
import com.example.hoax.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/v1/users")
    ResponseEntity<?> createUSer(@Valid @RequestBody User user) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatusCode(400);
        Map<String, String> validationErrors = new HashMap<>();

        if (user.username.isEmpty() || user.username == null) {
            validationErrors.put("username", "Username can not be empty!");

        }
        if (user.mail.isEmpty() || user.mail == null) {
            validationErrors.put("mail", "Mail address can not be empty!");
        }
        if (!validationErrors.isEmpty()) {

            apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
        }

        userService.save(user);
        return ResponseEntity.ok(new GenericMessage("User is created"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> argumentNotValid(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatusCode(400);
        Map<String, String> validationErrors = new HashMap<>();
        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }
}
