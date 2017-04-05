package com.tenPines;

import com.tenPines.application.service.AdminService;
import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.InitializerHerokuSystem;
import com.tenPines.model.InitializerLocalSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SecretPalConfig{

    @Autowired
    private WorkerService workerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Bean
    @Profile("local")
    public InitializerLocalSystem initializeLocal(){
        return new InitializerLocalSystem(workerService, userService, adminService);
    }

    @Bean
    @Profile("heroku")
    public InitializerHerokuSystem initializeHeroku(){
        return new InitializerHerokuSystem(workerService);
    }
}