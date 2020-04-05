package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.*;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.push.interfaces.GameEndedNotificationService;
import de.marvinbrieger.toothbrushgame.push.interfaces.MurderAssignmentNotificationService;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.NotGameOwnerException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.EnsureGameOwnerService;
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
    private final EnsureGameOwnerService ensureGameOwnerService;

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

        // the owner is also player in the game
        Player owner = game.getOwner();
        owner.setUser(currentUserService.getCurrentUser());
        game.getPlayers().add(owner);
        owner.setGame(game);

        return gameRepository.save(game);
    }



    @Override
    public Game startGame(Long id) {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.PREPARATION)
                .map(game -> {
                    ensureGameOwnerService.ensureRequestedByGameOwner(game);
                    game.setGameStatus(GameStatus.RUNNING);
                    List<MurderAssignment> murderAssignments = assignmentHelperService.generateKillAssignments(game);
        game.setMurderAssignments(murderAssignments);
        murderAssignmentNotificationService.pushMurderAssignments(murderAssignments);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.PREPARATION));
    }

    @Override
    public Game endGame(Long id) throws NotGameOwnerException {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.RUNNING)
                .map(game -> {
                    ensureGameOwnerService.ensureRequestedByGameOwner(game);
                    game.setGameStatus(GameStatus.FINISHED);
                    gameEndedNotificationService.pushGameEnding(game);
        return gameRepository.save(game);})
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.RUNNING));
    }

}
