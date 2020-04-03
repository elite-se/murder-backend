package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.*;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.push.interfaces.GameEndedNotificationService;
import de.marvinbrieger.toothbrushgame.push.interfaces.MurderAssignmentNotificationService;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.NoGameOwnerException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GameServiceImpl implements de.marvinbrieger.toothbrushgame.services.interfaces.GameService {
    private final GameRepository gameRepository;
    private final GameCodeService gameCodeService;
    private final AssignmentGeneratorService assignmentHelperService;
    private final CurrentUserService currentUserService;
    private final MurderAssignmentNotificationService murderAssignmentNotificationService;
    private final GameEndedNotificationService gameEndedNotificationService;

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

    @SneakyThrows(UserNotFoundException.class)
    @Override
    public Game createGame(Game game) {
        String gameCode = gameCodeService.getNewGameCode();
        game.setGameCode(gameCode);

        game.setPlayers(new ArrayList<>()); // api user could not add players
        game.setGameStatus(GameStatus.PREPARATION);

        // the creator is also player in the game
        Player creator = game.getOwner();
        creator.setUser(currentUserService.getCurrentUser());
        game.getPlayers().add(creator);
        creator.setGame(game);

        return gameRepository.save(game);
    }

    private void ensureRequestedByGameOwner(Game game) throws NoGameOwnerException {
        try {
            if (!currentUserService.getCurrentUser().equals(game.getOwner().getUser()))
                throw new NoGameOwnerException();
        } catch (UserNotFoundException e) {
            throw new NoGameOwnerException(e);
        }
    }

    @Override
    public Game startGame(Long id) throws NoGameOwnerException {
        var game = gameRepository.findByIdAndGameStatus(id, GameStatus.PREPARATION)
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.PREPARATION));
        ensureRequestedByGameOwner(game);
        game.setGameStatus(GameStatus.RUNNING);
        List<MurderAssignment> murderAssignments = assignmentHelperService.generateKillAssignments(game);
        game.setMurderAssignments(murderAssignments);
        murderAssignmentNotificationService.pushMurderAssignments(murderAssignments);
        return gameRepository.save(game);
    }

    @Override
    public Game endGame(Long id) throws NoGameOwnerException {
        Game game = gameRepository.findByIdAndGameStatus(id, GameStatus.RUNNING)
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.RUNNING));
        ensureRequestedByGameOwner(game);
        game.setGameStatus(GameStatus.FINISHED);
        gameEndedNotificationService.pushGameEnding(game);
        return gameRepository.save(game);
    }

}
