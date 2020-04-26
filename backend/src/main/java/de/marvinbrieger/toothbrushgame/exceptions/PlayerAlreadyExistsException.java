package de.marvinbrieger.toothbrushgame.exceptions;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String playerName) {
        super("A player with the name " + playerName + " already exists in the game");
    }

    public PlayerAlreadyExistsException(ApplicationUser user) {
        super("A player for the device " + user.getDeviceId() + " already exists in the game");
    }

}
