package com.iotiq.application.wiki.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException() {
        super("Refresh token has expired");
    }
}
