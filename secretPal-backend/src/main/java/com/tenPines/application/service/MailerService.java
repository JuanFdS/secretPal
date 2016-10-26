package com.tenPines.application.service;


import com.tenPines.mailer.UnsentMessage;
import com.tenPines.persistence.FailedMailsRepository;
import com.tenPines.utils.PropertyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Service
public class MailerService {

    private String mailTemplateProperties = "src/main/resources/mailTemplate.properties";

    @Autowired
    private FailedMailsRepository failedMailsRepository;

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

    public List<UnsentMessage> retrieveAllFailedMails(){
        return failedMailsRepository.findAll();
    }

}
