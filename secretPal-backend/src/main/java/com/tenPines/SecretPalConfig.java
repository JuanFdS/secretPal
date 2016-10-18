package com.tenPines;

import com.tenPines.application.service.WorkerService;
import com.tenPines.model.InitializerHerokuSystem;
import com.tenPines.model.InitializerLocalSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SecretPalConfig{

    @Autowired
    private WorkerService workerService;

    @Bean
    @Profile("local")
    public InitializerLocalSystem initializeLocal(){
        return new InitializerLocalSystem(workerService);
    }


    @Bean
    @Profile("heroku")
    public InitializerHerokuSystem initializeHeroku(){
        return new InitializerHerokuSystem(workerService);
    }
}