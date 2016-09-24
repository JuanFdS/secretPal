package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    private SecretPalSystem system;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Properties getMail() throws IOException {
        return system.getEMailTemplate();
    }

    @RequestMapping(value = "/failedMails", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getFailedMail(){
        return system.getFailedMails().retrieveAll();
    }
}
