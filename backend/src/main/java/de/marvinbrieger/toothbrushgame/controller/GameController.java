package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.services.interfaces.GameService;
import de.marvinbrieger.toothbrushgame.domain.Game;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * @see GameService
     *
     * @param id
     * @return
     */
    @GetMapping("/games/{id:[0-9]+}")
    Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    /**
     * @see GameService
     *
     * @param gameCode
     * @return
     */
    @GetMapping("/games/{gameCode:[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*}")
    Game getGameByGameCode(@PathVariable String gameCode) {
        return gameService.getGameByGameCode(gameCode);
    }

    /**
     * @see GameService
     *
     * @param game
     * @return
     */
    @PostMapping("/games")
    Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    /**
     * @see GameService
     *
     * @param id
     * @return
     */
    @PutMapping("/games/{id}/start")
    Game startGame(@PathVariable Long id) {
        return gameService.startGame(id);
    }

    /**
     * @see GameService
     *
     * @param id
     * @return
     */
    @PutMapping("/games/{id}/end")
    Game endGame(@PathVariable Long id) {
        return gameService.endGame(id);
    }

}
