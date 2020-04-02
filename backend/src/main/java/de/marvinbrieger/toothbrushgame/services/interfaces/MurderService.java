package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Murder;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.NotYourAssignmentException;

public interface MurderService {

    /**
     * Commits the specified murder in the specified game.
     *
     * @throws GameNotFoundException Is thrown if the specified game does not exist.
     *
     * @param gameId id of the game that the murder belongs to
     * @param assignmentId ID of the murder assignment the murder belongs to
     * @return the murder that was committed
     * @throws NotYourAssignmentException if the currently logged in user is not the assigned murderer
     */
    Murder commitMurder(Long gameId, Long assignmentId) throws NotYourAssignmentException;

}
