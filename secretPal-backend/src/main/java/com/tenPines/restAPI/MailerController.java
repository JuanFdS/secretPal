package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.service.MailerService;
import com.tenPines.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/mail")
public class MailerController {

    @Autowired
    private SecretPalSystem system;

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
        Properties mailerProperties = new Properties();
        mailerProperties.load(new FileReader("src/main/resources/mailTemplate.properties"));
        mailerProperties.setProperty("bodyText", modifiedMail.getProperty("bodyText"));
        mailerProperties.store(new FileWriter("src/main/resources/mailTemplate.properties"), LocalDateTime.now().toString());
        return mailerService.getEMailTemplate();
    }

    @RequestMapping(value = "/failedMails", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getFailedMail(){
        return system.getFailedMails().retrieveAll();
    }
}
