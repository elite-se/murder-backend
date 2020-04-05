package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.PlayerAlreadyExistsException;
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
        Game gameToJoin = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        if (!gameToJoin.inPreparation())
            throw new GameNotFoundException(gameId, GameStatus.PREPARATION);

        if (playerRepository.existsByGame_IdAndPlayerName(gameId, player.getPlayerName()))
            throw new PlayerAlreadyExistsException(player.getPlayerName());

        var currentUser = currentUserService.getCurrentUser();
        if (playerRepository.existsByGame_IdAndUser(gameId, currentUser))
            throw new PlayerAlreadyExistsException(currentUser);

        player.setGame(gameToJoin);
        player.setUser(currentUserService.getCurrentUser());
        return playerRepository.save(player);
    }

}
