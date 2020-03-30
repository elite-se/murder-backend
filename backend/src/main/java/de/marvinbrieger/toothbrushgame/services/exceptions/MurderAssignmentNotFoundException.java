package de.marvinbrieger.toothbrushgame.services.exceptions;

public class MurderAssignmentNotFoundException extends RuntimeException {

    public MurderAssignmentNotFoundException(Long murderAssignmentId) {
        super("Could not find murder assignment " + murderAssignmentId);
    }

}
