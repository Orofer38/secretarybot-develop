package io.onhigh.bot.secretary.gateway.tg.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.onhigh.bot.secretary.gateway.tg")
@Getter
@Setter
@ToString
public class BotConfig {

    @Value("${application.telegram.comfort.client.username}")
    private String botUsername;

    @Value("${application.telegram.comfort.client.token}")
    private String botToken;

}
