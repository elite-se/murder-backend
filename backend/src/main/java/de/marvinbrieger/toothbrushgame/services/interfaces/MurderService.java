package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Murder;

public interface MurderService {

    /**
     * Commits the specified murder in the specified game.
     *
     * @param gameId id of the game that the murder belongs to
     * @param assignmentId ID of the murder assignment the murder belongs to
     * @return the murder that was committed
     */
    Murder commitMurder(Long gameId, Long assignmentId);

}
