package de.marvinbrieger.toothbrushgame.unittests.services.exceptions;

public class NoGameCodeAvailableException extends RuntimeException {

    public NoGameCodeAvailableException() {
        super("Could not create a new game code");
    }

}
