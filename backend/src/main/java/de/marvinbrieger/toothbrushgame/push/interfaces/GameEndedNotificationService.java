package de.marvinbrieger.toothbrushgame.push.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Game;

/**
 * Sends push notifications for game endings
 */
public interface GameEndedNotificationService {
    /**
     * Informs the players of a game about its ending.
     *
     * If some push messages can not be delivered, the reasons will be logged.
     *
     * @param game the game that ended
     */
    void pushGameEnding(Game game);
}
