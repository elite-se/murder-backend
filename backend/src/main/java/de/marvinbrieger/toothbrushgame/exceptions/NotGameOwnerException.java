package de.marvinbrieger.toothbrushgame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotGameOwnerException extends RuntimeException {
    private static final String MESSAGE = "You have to be the game owner to perform this request";

    public NotGameOwnerException() {
        super(MESSAGE);
    }

    public NotGameOwnerException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
