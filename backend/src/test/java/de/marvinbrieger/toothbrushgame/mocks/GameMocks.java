package de.marvinbrieger.toothbrushgame.mocks;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.Player;

import java.util.ArrayList;
import java.util.List;

import static de.marvinbrieger.toothbrushgame.mocks.GamePreferencesMocks.STANDARD_PREFERENCES;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.ELIAS;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.MARVIN;

public class GameMocks {

    public static final Game SOFTSKILL_GAME
            = new Game(null, "Softskillkurs SE 14", STANDARD_PREFERENCES, null, ELIAS, null, null);

    public static final Game STORED_SOFTSKILL_GAME;

    public static final Game SOFTSKILL_GAME_MARVIN_JOINED;

    static {
        List<Player> storedPlayers = new ArrayList();
        storedPlayers.add(ELIAS);
        STORED_SOFTSKILL_GAME = new Game(1L, "Softskillkurs SE 14", STANDARD_PREFERENCES,
                "ANTZUF", ELIAS, GameStatus.PREPARATION, storedPlayers);

        List<Player> playersMarvinJoined = new ArrayList();
        playersMarvinJoined.add(ELIAS);
        playersMarvinJoined.add(MARVIN);
        SOFTSKILL_GAME_MARVIN_JOINED = new Game(1L, "Softskillkurs SE 14", STANDARD_PREFERENCES,
                "ANTZUF", ELIAS, GameStatus.PREPARATION, playersMarvinJoined);
    }

}
