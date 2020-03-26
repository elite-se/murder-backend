package de.marvinbrieger.toothbrushgame.unittests.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String playerName) {
        super("A player with the name " + playerName + " already exists in the game");
    }

}
