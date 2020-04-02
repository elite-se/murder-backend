package de.marvinbrieger.toothbrushgame.services.exceptions;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String playerName) {
        super("A player with the name " + playerName + " already exists in the game");
    }

    public PlayerAlreadyExistsException(ApplicationUser user) {
        super("A player for the device " + user.getDeviceId() + " already exists in the game");
    }

}
