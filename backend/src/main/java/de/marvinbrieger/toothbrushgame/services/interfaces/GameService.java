package de.marvinbrieger.toothbrushgame.services.interfaces;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;

public interface GameService {

    /**
     * Returns the game found by the given id.
     *
     * @param id id of the game
     * @return the game with that id
     */
    Game getGameById(Long id);

    /**
     * Returns the game found by the given game code.
     *
     * @throws GameNotFoundException Is thrown if there is no running game with
     * the given gameCode.
     *
     * @param gameCode the game code
     * @return the game with that code
     */
    Game getGameByGameCode(String gameCode);

    /**
     * Creates a new game and returns it.
     *
     * The owner of the given game is created as player along with the game
     * and added as player to the game.
     *
     * @param game details of the game that should be created
     * @return the created game
     */
    Game createGame(Game game);

    /**
     * Starts the specified game and returns it.
     *
     * @throws GameNotFoundException Is thrown if the state of the specified
     * game is inappropriate.
     *
     * @param id the ID of the game to start
     * @return the game with given ID
     */
    Game startGame(Long id);

    /**
     * Ends the specified game and returns it.
     *
     * @param id the ID of the game to end
     * @return the game with given ID
     */
    Game endGame(Long id);

}
