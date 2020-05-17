package de.marvinbrieger.toothbrushgame.webservice;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.services.interfaces.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {
    private final GameService gameService;

    /**
     * @see GameService#getGameById(Long)
     */
    @GetMapping("/{id:[0-9]+}")
    public Game getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    /**
     * @see GameService#getGameByGameCode(String)
     */
    @GetMapping("/{gameCode:[a-zA-Z0-9]*[a-zA-Z][a-zA-Z0-9]*}")
    public Game getGameByGameCode(@PathVariable String gameCode) {
        return gameService.getGameByGameCode(gameCode);
    }

    /**
     * @see GameService#createGame(Game)
     */
    @PostMapping()
    public Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    /**
     * @see GameService#startGame(Long)
     */
    @PutMapping("/{id}/start")
    public Game startGame(@PathVariable Long id) {
        return gameService.startGame(id);
    }

    /**
     * @see GameService#endGame(Long)
     */
    @PutMapping("/{id}/end")
    public Game endGame(@PathVariable Long id) {
        return gameService.endGame(id);
    }

}
