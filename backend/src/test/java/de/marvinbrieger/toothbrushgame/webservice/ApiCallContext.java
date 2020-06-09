package de.marvinbrieger.toothbrushgame.webservice;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ApiCallContext {

    private String bearerToken;

    private MockMvc mockMVC;

    public ApiCallContext(MockMvc mockMvc, JSONObject applicatonUser) throws Exception {
        this.mockMVC = mockMvc;

        mockMvc.perform(MockMvcRequestBuilders.post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(applicatonUser.toString()))
                .andDo(print());

        this.bearerToken = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(applicatonUser.toString()))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");
    }

    ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMVC.perform(builder
                .header("Authorization", bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
        ).andDo(print());
    }

    JSONObject performAndGetJson(MockHttpServletRequestBuilder builder) throws Exception {
        return new JSONObject(
                perform(builder)
                        .andReturn()
                        .getResponse()
                        .getContentAsString()
        );
    }

}
