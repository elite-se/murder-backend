package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.QGame;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundExeception;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements de.marvinbrieger.toothbrushgame.controller.interfaces.GameService {

    private final GameRepository gameRepository;

    private final GameCodeService gameCodeService;

    GameServiceImpl(GameRepository gameRepository, GameCodeService gameCodeService) {
        this.gameRepository = gameRepository;
        this.gameCodeService = gameCodeService;
    }

    @Override
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundExeception(id));
    }

    @Override
    public Game getGameByGameCode(String gameCode) {
        BooleanExpression pred = QGame
                .game.gameCode.eq(gameCode)
                .and(QGame.game
                        .gameStatus.ne(GameStatus.FINISHED));

        return gameRepository.findOne(pred)
                .orElseThrow(() -> new GameNotFoundExeception(gameCode));
    }

    @Override
    public Game createGame(Game game) {
        String gameCode = gameCodeService.getNewGameCode();
        game.setGameCode(gameCode);

        game.setPlayers(null); // api user could not add players
        game.setGameStatus(GameStatus.PREPARATION);

        game.getOwner().setGame(game); // the creator is also player in the game

        return gameRepository.save(game);
    }

    @Override
    public Game startGame(Long id) {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.PREPARATION)
                .map(game -> {
                    game.setGameStatus(GameStatus.RUNNING);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundExeception(id, GameStatus.PREPARATION));
    }

    @Override
    public Game endGame(Long id) {
        return gameRepository.findByIdAndGameStatus(id, GameStatus.RUNNING)
                .map(game -> {
                    game.setGameStatus(GameStatus.FINISHED);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameNotFoundExeception(id, GameStatus.RUNNING));
    }

}
