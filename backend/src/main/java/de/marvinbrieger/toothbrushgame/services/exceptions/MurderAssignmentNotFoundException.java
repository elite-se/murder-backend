package de.marvinbrieger.toothbrushgame.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MurderAssignmentNotFoundException extends RuntimeException {

    public MurderAssignmentNotFoundException(Long murderAssignmentId) {
        super("Could not find murder assignment " + murderAssignmentId);
    }

}
