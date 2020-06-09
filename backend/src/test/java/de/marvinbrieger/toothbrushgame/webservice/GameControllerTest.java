package de.marvinbrieger.toothbrushgame.webservice;

import de.marvinbrieger.toothbrushgame.services.interfaces.UserService;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
public class GameControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ApiCallContext marvinContext;

    private ApiCallContext eliasContext;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        marvinContext = new ApiCallContext(mockMvc, MARVINS_DEVICE);
        eliasContext = new ApiCallContext(mockMvc, ELIAS_DEVICE);
    }

    @Test
    public void testCreateGame() throws Exception {
        JSONObject creationAnswer = marvinContext.performAndGetJson(post("/games")
                .content(SOFTSKILL_GAME.toString()));

        String gameId = creationAnswer.getString("id");
        JSONObject game = marvinContext.performAndGetJson(get("/games/" + gameId));

        assertEquals("SE 14", game.getString("title"));
    }

    @Test
    public void testJoinGame() throws Exception {
        // Arrange
        JSONObject creationAnswer = marvinContext.performAndGetJson(post("/games")
                .content(SOFTSKILL_GAME.toString()));

        // Act
        String gameId = creationAnswer.getString("id");
        JSONObject player = marvinContext.performAndGetJson(post("/games/" + gameId + "/players")
                .content(ELIAS_PLAYER.toString()));

        // Assert
        JSONObject game = marvinContext.performAndGetJson(get("/games/" + gameId));
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
