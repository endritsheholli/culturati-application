package com.iotiq.application.wiki.exception;

public class NullArgumentException extends IllegalArgumentException {
    private NullArgumentException() {
        super("The argument can't be null");
    }

    public static void check(Object argumentValue) {
        if (argumentValue == null) {
            throw new NullArgumentException();
        }
    }
}
