package de.marvinbrieger.toothbrushgame.push.expobroadcasting;

import io.github.jav.exposerversdk.PushClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpoPushClientBean {
    @Bean
    public PushClient getPushClient() {
        return new PushClient();
    }
}
