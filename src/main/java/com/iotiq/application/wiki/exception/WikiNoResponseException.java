package com.iotiq.application.wiki.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class WikiNoResponseException extends ApplicationException {
    public WikiNoResponseException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "wikiNoResponse", Collections.emptyList());
    }
}
