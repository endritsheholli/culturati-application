package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;

public class TenantException extends ApplicationException {
    private static final Logger log = LoggerFactory.getLogger(TenantException.class);

    public TenantException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "tenantException", List.of(message));
        log.error(message);
    }
}
