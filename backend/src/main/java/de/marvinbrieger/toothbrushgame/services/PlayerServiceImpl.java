package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final CurrentUserService currentUserService;

    @Override
    public Player joinGame(Long gameId, Player player) {
        return gameRepository.findById(gameId)
                .map(game -> {
                    Player newPlayer = new Player(player, game, currentUserService.getCurrentUser());
                    game.addPlayer(newPlayer);
                    return playerRepository.save(newPlayer);
                })
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

}
