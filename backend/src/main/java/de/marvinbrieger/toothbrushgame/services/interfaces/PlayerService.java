package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Player;

public interface PlayerService {

    /**
     * Joins the specified game with the given player.
     */
    Player joinGame(Long gameId, Player player);

}
