package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Murder;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;

public interface MurderService {

    /**
     * Commits the specified murder in the specified game.
     *
     * @throws GameNotFoundException Is thrown if the specified game does not exist.
     *
     * @param gameId
     * @param assignmentId
     * @return
     */
    Murder commitMurder(Long gameId, Long assignmentId);

}
