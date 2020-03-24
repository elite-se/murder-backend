package de.marvinbrieger.toothbrushgame.controller;

import de.marvinbrieger.toothbrushgame.controller.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.services.GameCodeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameRepository gameRepository;

    private final GameCodeService gameCodeService;

    GameController(GameRepository gameRepository, GameCodeService gameCodeService) {
        this.gameRepository = gameRepository;
        this.gameCodeService = gameCodeService;
    }

    /**
     * Is used to get game information by id.
     *
     * @param id
     * @return
     */
    @GetMapping("/games/{id:[0-9]+}")
    Game getOne(@PathVariable Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundExeception(id));
    }

    /**
     * Is used to get game information by gameCode.
     *
     * @param gameCode
     * @return
     */
    @GetMapping("/games/{gameCode:[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*}")
    Game getOne(@PathVariable String gameCode) {
        return gameRepository.findByGameCode(gameCode)
                .orElseThrow(() -> new GameNotFoundExeception(gameCode));
    }

    /**
     * Is used to create a new game.
     *
     * The player of the game administrator is created along with the game.
     *
     * @param game
     * @return
     */
    @PostMapping("/games")
    Game createGame(@RequestBody Game game) {
        String gameCode = gameCodeService.getNewGameCode();
        game.setGameCode(gameCode);

        game.setDeleted(false); // api user could never create a deleted game
        game.setPlayers(null); // api user could not add players
        game.setGameStatus(GameStatus.PREPERATION);

        game.getOwner().setGame(game); // the creator is also player in the game

        return gameRepository.save(game);
    }

    /**
     * Is used to abort a game.
     *
     * @param id
     */
    @DeleteMapping("/games/{id}")
    void abortGame(@PathVariable Long id) {

    }

}
