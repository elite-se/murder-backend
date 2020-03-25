package de.marvinbrieger.toothbrushgame.unittests.services;

import de.marvinbrieger.toothbrushgame.controller.interfaces.PlayerService;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
import de.marvinbrieger.toothbrushgame.unittests.services.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.unittests.services.exceptions.PlayerAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final GameRepository gameRepository;

    PlayerServiceImpl(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Player joinGame(Long gameId, Player player) {
        Game gameToJoin = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundExeception(gameId));

        if (playerRepository.existsByGame_IdAndPlayerName(gameId, player.getPlayerName()))
            throw new PlayerAlreadyExistsException(player.getPlayerName());

        player.setGame(gameToJoin);
        return playerRepository.save(player);
    }

}
