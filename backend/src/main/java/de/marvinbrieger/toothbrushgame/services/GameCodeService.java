package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.QGame;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameCodeService {

    private final GameRepository gameRepository;

    public GameCodeService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    private String getRandomIdentifier(int length) {
        StringBuilder randomIdentifier = new StringBuilder();
        for (int j = 0; j < length; j++)
            randomIdentifier.append((char) ('A' + 26 * Math.random()));

        return randomIdentifier.toString();
    }

    public String getNewGameCode() {
        int length = 6;

        for (int failCount = 0; length < 100; failCount++) {
            String gameCode = getRandomIdentifier(length);


            gameRepository.exists(QGame.game.gameCode.eq(gameCode));


        }

        return null;
    }

}
