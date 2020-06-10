package de.marvinbrieger.toothbrushgame.mocks;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.domain.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class PlayerMocks {

    public static JSONObject ELIAS_PLAYER;

    static {
        try {
            ELIAS_PLAYER = new JSONObject()
                        .put("playerName", "Elias");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
