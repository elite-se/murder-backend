package de.marvinbrieger.toothbrushgame.mocks;

import org.json.JSONException;
import org.json.JSONObject;

public class GameMocks {

    public static JSONObject SOFTSKILL_GAME;

    static {
        try {
            SOFTSKILL_GAME = new JSONObject()
                    .put("title", "SE 14")
                    .put("preferences", new JSONObject()
                            .put("dailyReassignment", false)
                            .put("noAttestors", true)
                            .put("furtherRules", "nicht beim Essen töten"))
                    .put("owner", new JSONObject()
                            .put("playerName", "Marvin"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
