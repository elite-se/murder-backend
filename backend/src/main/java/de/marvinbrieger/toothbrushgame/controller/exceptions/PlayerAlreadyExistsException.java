package de.marvinbrieger.toothbrushgame.controller.exceptions;

import de.marvinbrieger.toothbrushgame.domain.Player;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String playerName) {
        super("A layer with the name " + playerName + " already exists in the game");
    }

}
