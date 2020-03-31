package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;

public interface GameService {

    /**
     * Returns the game found by the given id.
     *
     * @throws GameNotFoundException Is thrown if the game does not exist.
     *
     * @param id
     * @return
     */
    Game getGameById(Long id);

    /**
     * Returns the game found by the given game code.
     *
     * @throws GameNotFoundException Is thrown if there is no running game with
     * the given gameCode.
     *
     * @param gameCode
     * @return
     */
    Game getGameByGameCode(String gameCode);

    /**
     * Creates a new game and returns it.
     *
     * The owner of the given game is created as player along with the game
     * and added as player to the game.
     *
     * @param game
     * @return
     */
    Game createGame(Game game);

    /**
     * Starts the specified game and returns it.
     *
     * @throws GameNotFoundException Is thrown if the state of the specified
     * game is inappropriate.
     *
     * @param id
     * @return
     */
    Game startGame(Long id);

    /**
     * Ends the specified game and returns it.
     *
     * @throws GameNotFoundException Is thrown if the state of the specified
     * game is inappropriate.
     *
     * @param id
     * @return
     */
    Game endGame(Long id);

}
