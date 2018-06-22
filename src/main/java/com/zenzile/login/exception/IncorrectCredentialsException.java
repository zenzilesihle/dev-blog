package com.zenzile.login.exception;

public class IncorrectCredentialsException extends RuntimeException {
    public IncorrectCredentialsException(String invalid_username_and_password) {
        super(invalid_username_and_password);
    }
}
