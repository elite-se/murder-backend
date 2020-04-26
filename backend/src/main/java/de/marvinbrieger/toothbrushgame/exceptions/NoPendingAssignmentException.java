package de.marvinbrieger.toothbrushgame.exceptions;

import de.marvinbrieger.toothbrushgame.domain.Player;

public class NoPendingAssignmentException extends RuntimeException {
    public NoPendingAssignmentException(Player player) {
        super(String.format("No pending assignment for player with ID %d available", player.getId()));
    }
}
