package com.tenPines.application.service;


import com.tenPines.utils.PropertyParser;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

@Service
public class MailerService {

    private String mailTemplateProperties = "src/main/resources/mailTemplate.properties";

    public Properties getEMailTemplate() throws IOException {
        return new PropertyParser(mailTemplateProperties);
    }


    public Properties setEmailTemplate(Properties modifiedMail) throws IOException {
        Properties mailerProperties = ModifiedTemplate(modifiedMail);
        mailerProperties.store(new FileWriter(mailTemplateProperties), LocalDateTime.now().toString());
        return mailerProperties;
    }

    private Properties ModifiedTemplate(Properties modifiedMail) throws IOException {
        Properties mailerProperties = new Properties();
        mailerProperties.load(new FileReader(mailTemplateProperties));
        mailerProperties.setProperty("bodyText", modifiedMail.getProperty("bodyText"));
        mailerProperties.setProperty("subject", modifiedMail.getProperty("subject"));
        return mailerProperties;
    }
}
