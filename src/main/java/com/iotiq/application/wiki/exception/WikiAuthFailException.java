package com.iotiq.application.wiki.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class WikiAuthFailException extends ApplicationException {
    public WikiAuthFailException() {
        super(HttpStatus.UNAUTHORIZED, "wikiAuthFail", Collections.emptyList());
    }
}
