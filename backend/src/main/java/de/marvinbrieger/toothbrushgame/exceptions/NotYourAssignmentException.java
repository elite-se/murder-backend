package de.marvinbrieger.toothbrushgame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotYourAssignmentException extends RuntimeException {
    private static final String MESSAGE = "You are not allowed to modify murder assignments that do not belong to you";

    public NotYourAssignmentException() {
        super(MESSAGE);
    }

    public NotYourAssignmentException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
