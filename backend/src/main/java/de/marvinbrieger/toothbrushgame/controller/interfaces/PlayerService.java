package de.marvinbrieger.toothbrushgame.controller.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.unittests.services.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.unittests.services.exceptions.PlayerAlreadyExistsException;

public interface PlayerService {

    /**
     * Joins the specified game with the given player.
     *
     * @throws GameNotFoundExeception Is thrown if the specified game does not exist.
     * @throws PlayerAlreadyExistsException Is thrown if there is already a player with
     * the same name in the specified game.
     *
     * @param gameId
     * @param player
     * @return
     */
    Player joinGame(Long gameId, Player player);

}
