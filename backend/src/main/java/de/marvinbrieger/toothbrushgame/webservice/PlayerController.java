package de.marvinbrieger.toothbrushgame.webservice;

import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.services.interfaces.PlayerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * @see PlayerService
     *
     * @param gameId
     * @param player
     * @return
     */
    @PostMapping("/games/{gameId}/players")
    Player joinGame(@PathVariable Long gameId, @RequestBody Player player) {
        return playerService.joinGame(gameId, player);
    }

}
