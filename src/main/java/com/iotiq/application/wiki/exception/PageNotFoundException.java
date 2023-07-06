package com.iotiq.application.wiki.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String id) {
        super("asset with " + id + " not found");
    }

    public PageNotFoundException() {
        super("asset with not found");
    }
}
