package com.iotiq.application.wiki.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Could not authenticate");
    }
}
