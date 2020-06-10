package de.marvinbrieger.toothbrushgame.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MurderApiResponse {

    private MockHttpServletResponse response;

    public MurderApiResponse(MockHttpServletResponse response) {
        this.response = response;
    }

    public JSONObject getJsonAnswer() throws UnsupportedEncodingException, JSONException {
        return new JSONObject(response.getContentAsString());
    }

}
