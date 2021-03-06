package de.marvinbrieger.toothbrushgame.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.QGame;
import de.marvinbrieger.toothbrushgame.exceptions.NoGameCodeAvailableException;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for providing new game codes.
 */
@Service
public class GameCodeService {

    private final GameRepository gameRepository;

    public GameCodeService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    private static final int GAME_CODE_LENGTH = 6;

    private static final int FAILURE_THRESHOLD = 20;

    /**
     * Generates a random string of the given length.
     *
     * @param length
     * @return
     */
    private String getRandomIdentifier(int length) {
        StringBuilder randomIdentifier = new StringBuilder();
        for (int j = 0; j < length; j++)
            randomIdentifier.append((char) ('A' + 26 * Math.random()));

        return randomIdentifier.toString();
    }

    /**
     * Returns a new game identifier.
     *
     * @throws NoGameCodeAvailableException Is thrown if the number of attempts defined by FAILURE_THRESHOLD to
     * create a new game code failed. That means the reserve of unused game codes is exhausted with high
     * probability.
     *
     * @return
     */
    public String getNewGameCode() {
        for (int failCount = 0; failCount < FAILURE_THRESHOLD; failCount++) {
            String gameCode = getRandomIdentifier(GAME_CODE_LENGTH);
            BooleanExpression pred = QGame
                    .game.gameCode.eq(gameCode)
                    .and(QGame.game
                            .gameStatus.ne(GameStatus.FINISHED));

            if (gameRepository.exists(pred))
                failCount++;
            else
                return gameCode;
        }
        throw new NoGameCodeAvailableException();
    }

}
