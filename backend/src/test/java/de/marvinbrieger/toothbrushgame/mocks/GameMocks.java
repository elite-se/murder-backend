package de.marvinbrieger.toothbrushgame.mocks;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.KillAssignment;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.services.AssignmentHelperService;

import java.util.ArrayList;
import java.util.List;

import static de.marvinbrieger.toothbrushgame.mocks.GamePreferencesMocks.STANDARD_PREFERENCES;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.ALEX;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.ELIAS;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.KIPF;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.MARVIN;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.NEUMANN;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.STORED_ALEX;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.STORED_ELIAS;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.STORED_KIPF;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.STORED_MARVIN;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.STORED_NEUMANN;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.STORED_WINTER;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.WINTER;

public class GameMocks {

    public static final Game SOFTSKILL_GAME;

    public static final Game STORED_SOFTSKILL_GAME;

    public static final Game SOFTSKILL_GAME_MARVIN_JOINED;

    public static final Game SOFTSKILL_GAME_STARTED;

    static {
        SOFTSKILL_GAME = new Game(null, "Softskillkurs SE 14", STANDARD_PREFERENCES,
                null, ELIAS, null, null, null);

        List<Player> storedPlayers = new ArrayList();
        storedPlayers.add(STORED_ELIAS);
        STORED_SOFTSKILL_GAME = new Game(1L, "Softskillkurs SE 14", STANDARD_PREFERENCES,
                "ANTZUF", STORED_ELIAS, GameStatus.PREPARATION, storedPlayers, null);

        List<Player> playersMarvinJoined = new ArrayList();
        playersMarvinJoined.add(STORED_ELIAS);
        playersMarvinJoined.add(STORED_MARVIN);
        SOFTSKILL_GAME_MARVIN_JOINED = new Game(1L, "Softskillkurs SE 14", STANDARD_PREFERENCES,
                "ANTZUF", STORED_ELIAS, GameStatus.PREPARATION, playersMarvinJoined, null);

        List<Player> playersStartedGame = getPlayersList();
        Game startedGame = new Game(1L, "Softskillkurs SE 14", STANDARD_PREFERENCES,
                "ANTZUF", STORED_ELIAS, GameStatus.RUNNING, playersStartedGame, null);
        AssignmentHelperService assignmentHelperService = new AssignmentHelperService();
        List<KillAssignment> killAssignments = assignmentHelperService.generateKillAssignments(startedGame);
        startedGame.setKillAssignments(killAssignments);
        SOFTSKILL_GAME_STARTED = startedGame;
    }

    private static List<Player> getPlayersList() {
        List<Player> players = new ArrayList();
        players.add(STORED_ELIAS);
        players.add(STORED_MARVIN);
        players.add(STORED_ALEX);
        players.add(STORED_KIPF);
        players.add(STORED_WINTER);
        players.add(STORED_NEUMANN);
        return players;
    }

}
