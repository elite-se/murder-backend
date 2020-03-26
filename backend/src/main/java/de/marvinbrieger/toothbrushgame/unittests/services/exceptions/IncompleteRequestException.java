package de.marvinbrieger.toothbrushgame.unittests.services.exceptions;

public class IncompleteRequestException extends RuntimeException {
    public IncompleteRequestException() {
        super("Request was incomplete");
    }

    public IncompleteRequestException(String... missingParts) {
        super("Request was incomplete, missing: " + String.join(", ", missingParts));
    }
}
