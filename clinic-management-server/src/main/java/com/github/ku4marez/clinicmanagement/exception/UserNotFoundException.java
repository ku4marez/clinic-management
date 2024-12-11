package com.github.ku4marez.clinicmanagement.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String identifier) {
        super("User", identifier);
    }
}
