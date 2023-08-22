package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class FileFormatNotSupportedException extends ApplicationException {

    public FileFormatNotSupportedException(String fileExtension) {
        super(HttpStatus.BAD_REQUEST, "fileFormatNotSupported", Collections.emptyList(), new String[]{fileExtension});
    }
}
