package com.iotiq.application.wiki.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class WikiException extends ApplicationException {
    public WikiException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "wikiException", Collections.emptyList());
    }
}
