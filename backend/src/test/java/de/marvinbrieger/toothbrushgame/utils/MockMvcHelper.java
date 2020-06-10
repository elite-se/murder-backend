package de.marvinbrieger.toothbrushgame.utils;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

abstract public class MockMvcHelper {

    public static MockHttpServletRequestBuilder withJsonHeaders(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8");
    }

}
