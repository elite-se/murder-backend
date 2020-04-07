package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Game;

/**
 * Provides a function that ensures that the current request comes from the owner of a given game.
 */
public interface EnsureGameOwnerService {
    /**
     * Checks if the owner of the given game is the currently logged in user. Throws an exception otherwise.
     *
     * @param game the game to check the owner of
     */
    void ensureRequestedByGameOwner(Game game);
}
