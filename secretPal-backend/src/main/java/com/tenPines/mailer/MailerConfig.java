package com.tenPines.mailer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MailerConfig {
    @Bean
    @Profile("!test")
    public PostMan aSMTPPostMan() {
        return new SMTPPostMan();
    }

    @Bean
    @Profile("test")
    public PostMan aSillyPostMan() {
        return new InMemoryPostMan();
    }
}
