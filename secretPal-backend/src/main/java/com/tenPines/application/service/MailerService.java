package com.tenPines.application.service;


import com.tenPines.mailer.UnsentMessage;
import com.tenPines.model.EmailTemplate;
import com.tenPines.persistence.EmailTemplateRepository;
import com.tenPines.persistence.FailedMailsRepository;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MailerService {

    @Autowired
    private FailedMailsRepository failedMailsRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;


    public EmailTemplate getEMailTemplate() throws IOException {
        EmailTemplate emailTemplate = new EmailTemplate();
        if(!emailTemplateRepository.findAll().isEmpty())
            emailTemplate = emailTemplateRepository.findAll().get(0);
        return emailTemplate;

    }


    public EmailTemplate setEmailTemplate(EmailTemplate modifiedMail) throws IOException {
        EmailTemplate mailerProperties = ModifiedTemplate(modifiedMail);
        return mailerProperties;
    }

    private EmailTemplate ModifiedTemplate(EmailTemplate modifiedMail) throws IOException {
        EmailTemplate emailActual = getEMailTemplate();
        emailActual.modifiedTemplate(modifiedMail);
        saveTemplate(emailActual);
        return emailActual;
    }

    private void saveTemplate(EmailTemplate emailActual) {
        emailTemplateRepository.save(emailActual);
    }

    public List<UnsentMessage> retrieveAllFailedMails(){
        return failedMailsRepository.findAll();
    }

}
