package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class GeoJsonFileNotFoundException extends ApplicationException {

    public GeoJsonFileNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, "file_not_found", Collections.singletonList(message));

    }
}
