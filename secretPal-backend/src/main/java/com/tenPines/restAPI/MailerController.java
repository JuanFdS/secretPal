package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.service.MailerService;
import com.tenPines.mailer.UnsentMessage;
import com.tenPines.persistence.FailedMailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/mail")
public class MailerController {


    @Autowired
    private MailerService mailerService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Properties getMail() throws IOException {
        return mailerService.getEMailTemplate();
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Properties setMail(@RequestBody Properties modifiedMail) throws IOException {
        return mailerService.setEmailTemplate(modifiedMail);
    }


    @RequestMapping(value = "/failedMails", method = RequestMethod.GET)
    @ResponseBody
    public List<UnsentMessage> getFailedMail(){
        return mailerService.retrieveAllFailedMails();}
}
