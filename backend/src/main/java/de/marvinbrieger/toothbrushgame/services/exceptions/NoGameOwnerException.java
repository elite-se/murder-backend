package de.marvinbrieger.toothbrushgame.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoGameOwnerException extends Exception {
    private static final String MESSAGE = "You have to be the game owner to perform this request";

    public NoGameOwnerException() {
        super(MESSAGE);
    }

    public NoGameOwnerException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
