package de.marvinbrieger.toothbrushgame.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadySignedUpException extends RuntimeException {
    public AlreadySignedUpException(String deviceId) {
        super("A device with ID " + deviceId + " is already registered.");
    }
}
