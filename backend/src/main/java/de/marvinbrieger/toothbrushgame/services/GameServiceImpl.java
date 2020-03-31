package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.QGame;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameServiceImpl implements de.marvinbrieger.toothbrushgame.services.interfaces.GameService {

    private final GameRepository gameRepository;

    private final GameCodeService gameCodeService;

    private final AssignmentGeneratorService assignmentHelperService;

    GameServiceImpl(GameRepository gameRepository, GameCodeService gameCodeService, AssignmentGeneratorService assignmentHelperService) {
        this.gameRepository = gameRepository;
        this.gameCodeService = gameCodeService;
        this.assignmentHelperService = assignmentHelperService;
    }

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

        // the creator is also player in the game
        game.getPlayers().add(game.getOwner());
        game.getOwner().setGame(game);

        return gameRepository.save(game);
    }

    @Override
    public Game startGame(Long id) {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.PREPARATION)
                .map(game -> {
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
                    game.setGameStatus(GameStatus.FINISHED);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundException(id, GameStatus.RUNNING));
    }

}
