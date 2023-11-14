package com.example.hoax.user.exception;

public class InvalidActivationTokenException extends RuntimeException {
    public InvalidActivationTokenException() {
        super("Activation Token Invalid");
    }
}
