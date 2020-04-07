package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.domain.QGame;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.NoGameOwnerException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class GameServiceImpl implements de.marvinbrieger.toothbrushgame.services.interfaces.GameService {
    private final GameRepository gameRepository;
    private final GameCodeService gameCodeService;
    private final AssignmentGeneratorService assignmentHelperService;
    private final CurrentUserService currentUserService;

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Game getGameByGameCode(String gameCode) {
        BooleanExpression pred = QGame
                .game.gameCode.eq(gameCode)
                .and(QGame.game
                        .gameStatus.ne(GameStatus.FINISHED));

        return gameRepository.findOne(pred)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
    }

    @Override
    public Game createGame(Game game) {
        String gameCode = gameCodeService.getNewGameCode();
        game.setGameCode(gameCode);

        game.setPlayers(new ArrayList<>()); // api user could not add players
        game.setGameStatus(GameStatus.PREPARATION);

        // the owner is also player in the game
        Player owner = game.getOwner();
        owner.setUser(currentUserService.getCurrentUser());
        game.getPlayers().add(owner);
        owner.setGame(game);

        return gameRepository.save(game);
    }

    private void ensureRequestedByGameOwner(Game game) {
        try {
            if (!currentUserService.getCurrentUser().equals(game.getOwner().getUser()))
                throw new NoGameOwnerException();
        } catch (UserNotFoundException e) {
            throw new NoGameOwnerException(e);
        }
    }

    @Override
    public Game startGame(Long id) {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.PREPARATION)
                .map(game -> {
                    ensureRequestedByGameOwner(game);
                    game.setGameStatus(GameStatus.RUNNING);
                    game.setMurderAssignments(assignmentHelperService.generateKillAssignments(game));
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.PREPARATION));
    }

    @Override
    public Game endGame(Long id) {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.RUNNING)
                .map(game -> {
                    ensureRequestedByGameOwner(game);
                    game.setGameStatus(GameStatus.FINISHED);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.RUNNING));

    }

}
