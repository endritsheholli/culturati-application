package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String entityName) {
        super(HttpStatus.NOT_FOUND, "entityNotFound", List.of(entityName));
    }
}
