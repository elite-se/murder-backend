package de.marvinbrieger.toothbrushgame.webservice;

import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
import de.marvinbrieger.toothbrushgame.utils.DbCleaningHelper;
import de.marvinbrieger.toothbrushgame.utils.MurderApiContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import static de.marvinbrieger.toothbrushgame.mocks.ApplicationUserMocks.ELIAS_DEVICE;
import static de.marvinbrieger.toothbrushgame.mocks.ApplicationUserMocks.MARVINS_DEVICE;
import static de.marvinbrieger.toothbrushgame.mocks.GameMocks.SOFTSKILL_GAME;
import static de.marvinbrieger.toothbrushgame.mocks.PlayerMocks.ELIAS_PLAYER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Commit
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    DataSource dataSource;

    private MockMvc mockMvc;

    private MurderApiContext marvinContext;

    private MurderApiContext eliasContext;

    @Before
    public void init() throws Exception {
        DbCleaningHelper.truncateAllTables(dataSource.getConnection());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        marvinContext = new MurderApiContext(mockMvc, MARVINS_DEVICE);
        eliasContext = new MurderApiContext(mockMvc, ELIAS_DEVICE);
    }

    @Test
    public void testCreateGame() throws Exception {
        JSONObject game = marvinContext.createPost("/games")
                .content(SOFTSKILL_GAME)
                .send()
                .getJsonAnswer();

        game = marvinContext.createGet("/games/" + game.getString("id"))
                .send()
                .getJsonAnswer();

        assertEquals("SE 14", game.getString("title"));
    }

    @Test
    public void testJoinGame() throws Exception {
        // Arrange
        JSONObject game = marvinContext.createPost("/games")
                .content(SOFTSKILL_GAME)
                .send()
                .getJsonAnswer();

        // Act
        JSONObject player = eliasContext.createPost("/games/" + game.getString("id") + "/players")
                .content(ELIAS_PLAYER)
                .send()
                .getJsonAnswer();

        game = eliasContext.createGet("/games/" + game.getString("id"))
                .send()
                .getJsonAnswer();

        // Assert
        JSONArray playersArray = game.getJSONArray("players");
        boolean found = false;
        for (int k = 0; k < playersArray.length(); k++) {
            JSONObject joinedPlayer = playersArray.getJSONObject(k);
            if (joinedPlayer.getString("playerName").equals(ELIAS_PLAYER.getString("playerName")))
                found = true;
        }

        if (!found)
            fail("player should be contained after joining");
    }

}
