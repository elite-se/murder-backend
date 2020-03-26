package de.marvinbrieger.toothbrushgame.unittests.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NoGameCodeAvailableException extends RuntimeException {
    public NoGameCodeAvailableException() {
        super("Could not create a new game code");
    }
}
