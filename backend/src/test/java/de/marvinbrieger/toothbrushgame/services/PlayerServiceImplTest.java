package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.controller.interfaces.GameService;
import de.marvinbrieger.toothbrushgame.controller.interfaces.PlayerService;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundExeception;
import de.marvinbrieger.toothbrushgame.services.exceptions.PlayerAlreadyExistsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static de.marvinbrieger.toothbrushgame.mocks.GameMocks.SOFTSKILL_GAME;
import static de.marvinbrieger.toothbrushgame.mocks.GameMocks.STORED_SOFTSKILL_GAME;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.ELIAS;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.MARVIN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PlayerServiceImplTest {

    private PlayerService playerService;

    @Before
    public void setup() {
        PlayerRepository playerRepository = mock(PlayerRepository.class);
        when(playerRepository.existsByGame_IdAndPlayerName(1L, "Elias")).thenReturn(true);
        when(playerRepository.existsByGame_IdAndPlayerName(1L, "Marvin")).thenReturn(false);

        GameRepository gameRepository = mock(GameRepository.class);
        Optional<Game> standardGame = Optional.of(STORED_SOFTSKILL_GAME);
        when(gameRepository.findById(1L)).thenReturn(standardGame);
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());

        this.playerService = new PlayerServiceImpl(playerRepository, gameRepository);
    }

    @Test(expected = PlayerAlreadyExistsException.class)
    public void ownerShouldJoin_ThrowsException() {
        playerService.joinGame(1L, ELIAS);
    }

    @Test(expected = GameNotFoundExeception.class)
    public void joinToMissingGame_ThrowsException() {
        playerService.joinGame(2L, MARVIN);
    }

    public void joinToGame_Succeeds() {
        playerService.joinGame(1L, MARVIN);
    }

}
