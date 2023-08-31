package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collections;

public class FileNotFoundException extends ApplicationException {

    private final transient Logger log = LoggerFactory.getLogger(FileNotFoundException.class);

    public FileNotFoundException(String filename) {
        super(HttpStatus.NOT_FOUND, "fileNotFound", Collections.emptyList());

        log.error("Could not find file " + filename);
    }

    public FileNotFoundException(String filename, IOException ioException) {
        this(filename);

        log.error(ioException.getMessage());
    }
}
