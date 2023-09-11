package com.iotiq.application.exception;

import com.iotiq.commons.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class GameException extends ApplicationException {

    public GameException(String gameException, Object... args) {
        super(HttpStatus.BAD_REQUEST, "game", List.of(gameException), args);
    }
}
