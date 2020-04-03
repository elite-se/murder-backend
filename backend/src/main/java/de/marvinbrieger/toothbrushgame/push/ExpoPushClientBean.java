package de.marvinbrieger.toothbrushgame.push;

import io.github.jav.exposerversdk.PushClient;
import org.springframework.context.annotation.Bean;

public final class ExpoPushClientBean {
    private ExpoPushClientBean() {}

    @Bean
    public PushClient getPushClient() {
        return new PushClient();
    }
}
