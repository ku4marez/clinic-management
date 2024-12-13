package com.github.ku4marez.clinicmanagement.exception;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String identifier) {
        super("User", identifier);
    }
}
