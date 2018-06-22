package com.zenzile.admin.exception;

public class MissingEmailException extends RuntimeException{
    public MissingEmailException(String please_provide_admin_email) {
        super(please_provide_admin_email);
    }
}
