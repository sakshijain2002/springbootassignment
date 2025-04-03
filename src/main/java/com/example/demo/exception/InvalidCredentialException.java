package com.example.demo.exception;


import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialException extends AuthenticationException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}

