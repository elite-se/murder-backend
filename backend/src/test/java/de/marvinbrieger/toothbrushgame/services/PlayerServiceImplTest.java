package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.controller.interfaces.GameService;
import de.marvinbrieger.toothbrushgame.controller.interfaces.PlayerService;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.PlayerRepository;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PlayerServiceImplTest {

    private PlayerService playerService;

    @Before
    public void setup() {
        PlayerRepository playerRepository = mock(PlayerRepository.class);
        when(playerRepository.existsByGame_IdAndPlayerName(1L, "Elias")).thenReturn(true);

        GameRepository gameRepository = mock(GameRepository.class);
        Optional<Game> standardGame = Optional.of(STORED_SOFTSKILL_GAME);
        when(gameRepository.findById(1L)).thenReturn(standardGame);

        this.playerService = new PlayerServiceImpl(playerRepository, gameRepository);
    }

    @Test(expected = PlayerAlreadyExistsException.class)
    public void createGame_ownerIsAddedAsPlayer() {
        playerService.joinGame(1L, ELIAS);
    }

}
