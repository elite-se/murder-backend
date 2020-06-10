package de.marvinbrieger.toothbrushgame.utils;

import org.json.JSONObject;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MurderApiRequest {

    private MockMvc mockMvc;

    private MockHttpServletRequestBuilder requestBuilder;

    private boolean print;

    public MurderApiRequest(MockMvc mockMvc, MockHttpServletRequestBuilder requestBuilder, boolean print) throws Exception {
        this.mockMvc = mockMvc;
        this.requestBuilder = requestBuilder;
        this.print = print;
    }

    public MurderApiRequest content(JSONObject payload) {
        requestBuilder.content(payload.toString());
        return this;
    }

    public MurderApiResponse send() throws Exception {
        ResultActions result = mockMvc.perform(requestBuilder);
        if (print)
            result.andDo(print());

        return new MurderApiResponse(result.andReturn().getResponse());
    }

}
