package com.tenPines.mailer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MailerConfig {
    @Bean
    @Profile("real-mail")
    public PostMan aSMTPPostMan() {
        return new SMTPPostMan();
    }

    @Bean
    @Profile("!real-mail")
    public PostMan aSillyPostMan() {
        return message -> {
            // Do nothing
        };
    }
}
