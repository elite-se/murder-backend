package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.services.interfaces.PlayerService;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.PlayerAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static de.marvinbrieger.toothbrushgame.mocks.GameMocks.STORED_SOFTSKILL_GAME;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.ELIAS;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.MARVIN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PlayerServiceImplTest {

    private PlayerService playerService;

    private final Long EXISTING_ID = 1L;

    private final Long MISSING_ID = 2L;

    @Before
    public void setup() {
        PlayerRepository playerRepository = mock(PlayerRepository.class);
        when(playerRepository.existsByGame_IdAndPlayerName(EXISTING_ID, "Elias")).thenReturn(true);
        when(playerRepository.existsByGame_IdAndPlayerName(EXISTING_ID, "Marvin")).thenReturn(false);

        GameRepository gameRepository = mock(GameRepository.class);
        Optional<Game> standardGame = Optional.of(STORED_SOFTSKILL_GAME);
        when(gameRepository.findById(EXISTING_ID)).thenReturn(standardGame);
        when(gameRepository.findById(MISSING_ID)).thenReturn(Optional.empty());

        this.playerService = new PlayerServiceImpl(playerRepository, gameRepository);
    }

    @Test(expected = PlayerAlreadyExistsException.class)
    public void ownerShouldJoin_ThrowsException() {
        playerService.joinGame(EXISTING_ID, ELIAS);
    }

    @Test(expected = GameNotFoundException.class)
    public void joinToMissingGame_ThrowsException() {
        playerService.joinGame(MISSING_ID, MARVIN);
    }

    @Test
    public void joinToGame_Succeeds() {
        playerService.joinGame(EXISTING_ID, MARVIN);
    }

}
