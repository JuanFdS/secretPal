package com.tenPines.application.service;


import com.tenPines.utils.PropertyParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public class MailerService {

    private String mailTemplateProperties = "src/main/resources/mailTemplate.properties";

    public Properties getEMailTemplate() throws IOException {
        return new PropertyParser(mailTemplateProperties);
    }




}
