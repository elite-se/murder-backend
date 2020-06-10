package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.QGame;
import de.marvinbrieger.toothbrushgame.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.push.messagebuilders.GameEndedNotificationService;
import de.marvinbrieger.toothbrushgame.push.messagebuilders.MurderAssignmentNotificationService;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.EnsureGameOwnerService;
import de.marvinbrieger.toothbrushgame.services.interfaces.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
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

    @Override
    public Game createGame(Game game) {
        String gameCode = gameCodeService.getNewGameCode();
        Game newGame = new Game(game, gameCode, currentUserService.getCurrentUser());
        return gameRepository.save(newGame);
    }

    @Override
    public Game startGame(Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    ensureGameOwnerService.ensureRequestedByGameOwner(game);
                    List<MurderAssignment> murderAssignments = assignmentHelperService.generateKillAssignments(game);
                    game.start(murderAssignments);
                    murderAssignmentNotificationService.pushMurderAssignments(murderAssignments);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Game endGame(Long id) {
        return gameRepository.findById(id)
                .map(game -> {
                    ensureGameOwnerService.ensureRequestedByGameOwner(game);
                    game.end();
                    gameEndedNotificationService.pushGameEnding(game);
        return gameRepository.save(game);})
                .orElseThrow(() -> new GameNotFoundException(id));
    }

}
