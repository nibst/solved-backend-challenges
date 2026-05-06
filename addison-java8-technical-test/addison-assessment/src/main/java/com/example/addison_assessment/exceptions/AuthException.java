package com.example.addison_assessment.exceptions;

// unchecked exception to be treated globally
public class AuthException extends RuntimeException{
    public AuthException(String message){
        super(message);
    }
}
