package com.iotiq.application.wiki.exception;

import org.apache.commons.lang3.ObjectUtils;

public class BlankArgumentException extends IllegalArgumentException {
    private BlankArgumentException() {
        super("The argument can't be null");
    }

    public static void check(Object argumentValue) {
        if (ObjectUtils.isEmpty(argumentValue)) {
            throw new BlankArgumentException();
        }
    }
}
