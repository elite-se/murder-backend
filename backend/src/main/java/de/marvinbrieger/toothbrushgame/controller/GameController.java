package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.controller.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.services.GameCodeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class GameController {

    private final GameRepository gameRepository;

    GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games")
    Collection<Game> getAll() {
        return gameRepository.findAll();
    }

    @GetMapping("/games/{id:[0-9]+}")
    Game getOne(@PathVariable Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundExeception(id));
    }

    @GetMapping("/games/{gameCode:[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*}")
    Game getOne(@PathVariable String gameCode) {
        return gameRepository.findByGameCode(gameCode)
                .orElseThrow(() -> new GameNotFoundExeception(gameCode));
    }

    @PostMapping("/games")
    Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @DeleteMapping("/games/{id}")
    void abortGame(@PathVariable Long id) {

    }

}