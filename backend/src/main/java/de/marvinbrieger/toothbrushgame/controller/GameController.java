package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.services.interfaces.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games/{id:[0-9]+}")
    Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/games/{gameCode:[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*}")
    Game getGameByGameCode(@PathVariable String gameCode) {
        return gameService.getGameByGameCode(gameCode);
    }

    @PostMapping("/games")
    Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    @PutMapping("/games/{id}/start")
    Game startGame(@PathVariable Long id) {
        return gameService.startGame(id);
    }

    @PutMapping("/games/{id}/end")
    Game endGame(@PathVariable Long id) {
        return gameService.endGame(id);
    }

}
