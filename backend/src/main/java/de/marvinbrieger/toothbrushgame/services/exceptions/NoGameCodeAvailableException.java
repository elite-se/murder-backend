package de.marvinbrieger.toothbrushgame.services.exceptions;

public class NoGameCodeAvailableException extends RuntimeException {

    public NoGameCodeAvailableException() {
        super("Could not create a new game code");
    }

}
