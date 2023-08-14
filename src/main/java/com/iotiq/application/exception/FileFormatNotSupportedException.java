package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class FileFormatNotSupportedException extends ApplicationException {
    
    public FileFormatNotSupportedException(String message) {
        super(HttpStatus.BAD_REQUEST, "allowedExtensions", Collections.singletonList(message));
    }
}
