package de.marvinbrieger.toothbrushgame.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String deviceId) {
        super("No user found for device ID " + deviceId);
    }
}