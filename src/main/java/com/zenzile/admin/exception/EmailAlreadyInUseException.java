package com.zenzile.admin.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email_address_already_in_use) {
        super(email_address_already_in_use);
    }
}
