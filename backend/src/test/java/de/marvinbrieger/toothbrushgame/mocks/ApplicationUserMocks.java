package de.marvinbrieger.toothbrushgame.mocks;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationUserMocks {

    public static JSONObject MARVINS_DEVICE;

    public static JSONObject ELIAS_DEVICE;

    static {
        try {
            MARVINS_DEVICE = new JSONObject()
                    .put("deviceId", "marvins device")
                    .put("password", "marvins password");

            ELIAS_DEVICE = new JSONObject()
                    .put("deviceId", "elias device")
                    .put("password", "elias password");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
