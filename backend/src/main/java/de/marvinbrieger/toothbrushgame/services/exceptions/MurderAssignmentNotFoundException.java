package de.marvinbrieger.toothbrushgame.services.exceptions;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MurderAssignmentNotFoundException extends RuntimeException {

    public MurderAssignmentNotFoundException(Long murderAssignmentId) {
        super("Could not find murder assignment " + murderAssignmentId);
    }

    public MurderAssignmentNotFoundException(Long murderAssignmentId, MurderAssignmentStatus destStatus) {
        super(String.format("Could not find murder assignment %d or assignment is not in state %s", murderAssignmentId, destStatus));
    }

}
