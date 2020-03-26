package de.marvinbrieger.toothbrushgame.services.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String playerName) {
        super("A player with the name " + playerName + " already exists in the game");
    }

}
