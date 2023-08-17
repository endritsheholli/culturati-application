package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class FileNotFound extends ApplicationException {

    private final transient Logger log = LoggerFactory.getLogger(TenantDataSourceConnectionException.class);

    public FileNotFound(String filename) {
        super(HttpStatus.NOT_FOUND, "fileNotFound", Collections.emptyList());

        log.error("Could not find file " + filename);
    }
}
