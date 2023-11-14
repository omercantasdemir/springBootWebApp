package com.example.hoax.user;

import com.example.hoax.dto.UserCreate;
import com.example.hoax.error.ApiError;
import com.example.hoax.shared.GenericMessage;
import com.example.hoax.user.exception.InvalidActivationTokenException;
import com.example.hoax.user.exception.NotUniqueMailException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {


    UserService userService;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users/all")
    ResponseEntity<?> listUsers() {
        List<User> userList = userService.getUsers();
        return ResponseEntity.ok().body(userList);
    }

    //    @PostMapping("/api/v1/users/search")
//    ResponseEntity<?> getUser(){
//        userService.
//    }
    @PostMapping("/api/v1/users")
    ResponseEntity<?> createUSer(@Valid @RequestBody UserCreate user) {
        userService.save(user.toUser());
        return ResponseEntity.ok(new GenericMessage("User is created"));
    }

    @PatchMapping("/api/v1/users/{token}/activate")
    ResponseEntity<?> activateUsers(@PathVariable String token) {
        userService.activateUser(token);
        return ResponseEntity.ok(new GenericMessage("User is activated"));
    }

    @ExceptionHandler(NotUniqueMailException.class)
    ResponseEntity<ApiError> handleDuplicateMail(NotUniqueMailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatusCode(400);
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("mail", "E-mail kullanılıyor");
        apiError.setValidationErrors((validationErrors));
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> argumentNotValid(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatusCode(400);

        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacing) -> existing));
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(InvalidActivationTokenException.class)
    ResponseEntity<ApiError> tokenNotValid(InvalidActivationTokenException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(400);
        
        return ResponseEntity.status(400).body(apiError);
    }

}
