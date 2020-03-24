package de.marvinbrieger.toothbrushgame.controller;

import com.querydsl.core.types.Predicate;
import de.marvinbrieger.toothbrushgame.controller.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.controller.exceptions.PlayerAlreadyExistsException;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.domain.QPlayer;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {

    private final PlayerRepository playerRepository;

    private final GameRepository gameRepository;

    PlayerController(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/games/{gameId}/players")
    Iterable<Player> getAll(@PathVariable Long gameId) {
        Predicate pred = QPlayer.player.game.id.eq(gameId);
        return playerRepository.findAll(pred);
    }

    @PostMapping("/games/{gameId}/players")
    Player joinGame(@PathVariable Long gameId, @RequestBody Player player) {
        Game gameToJoin = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundExeception(gameId));

        if (playerRepository.existsByGame_Id(gameId))
            throw new PlayerAlreadyExistsException(player.getPlayerName());

        player.setGame(gameToJoin);
        return playerRepository.save(player);
    }

}
