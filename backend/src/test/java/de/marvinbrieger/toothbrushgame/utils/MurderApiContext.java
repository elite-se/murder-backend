package de.marvinbrieger.toothbrushgame.utils;

import org.json.JSONObject;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static de.marvinbrieger.toothbrushgame.utils.MockMvcHelper.withJsonHeaders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MurderApiContext {

    private String bearerToken;

    private MockMvc mockMvc;

    private boolean print;

    public MurderApiContext(MockMvc mockMvc, JSONObject applicationUser) throws Exception {
        this(mockMvc, applicationUser, false);
    }

    public MurderApiContext(MockMvc mockMvc, JSONObject applicationUser, boolean print) throws Exception {
        this.mockMvc = mockMvc;
        this.print = print;

        ResultActions signUp = mockMvc.perform(
                withJsonHeaders(post("/user/sign-up"))
                        .content(applicationUser.toString()));
        ResultActions login = mockMvc.perform(
                withJsonHeaders(post("/user/login"))
                        .content(applicationUser.toString()));

        if (print) {
            signUp.andDo(print());
            login.andDo(print());
        }

        this.bearerToken = login.andReturn()
                .getResponse()
                .getHeader("Authorization");
    }

    public MurderApiRequest createGet(String route) throws Exception {
        return new MurderApiRequest(mockMvc, withJsonHeaders(get(route))
                .header("Authorization", bearerToken), print);
    }

    public MurderApiRequest createPost(String route) throws Exception {
        return new MurderApiRequest(mockMvc, withJsonHeaders(post(route))
                .header("Authorization", bearerToken), print);
    }

}
