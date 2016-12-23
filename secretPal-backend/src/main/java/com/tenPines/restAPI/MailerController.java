package com.tenPines.restAPI;

import com.tenPines.application.SystemPalFacade;
import com.tenPines.application.service.MailerService;
import com.tenPines.mailer.UnsentMessage;
import com.tenPines.model.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/mail")
public class MailerController {


    @Autowired
    private MailerService mailerService;

    @Autowired
    private SystemPalFacade system;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public EmailTemplate getMail() throws IOException {
        return mailerService.getEMailTemplate();
    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public EmailTemplate setMail(@RequestBody EmailTemplate modifiedMail) throws IOException {
        return mailerService.setEmailTemplate(modifiedMail);
    }


    @RequestMapping(value = "/failedMails", method = RequestMethod.GET)
    @ResponseBody
    public List<UnsentMessage> getFailedMail(){
        return mailerService.retrieveAllFailedMails();}



    @RequestMapping(value = "/resendMailsFailure", method = RequestMethod.POST)
    @ResponseBody
    public void resendMail(@RequestBody UnsentMessage unsentMessage) throws IOException {
        system.resendMessageFailure(unsentMessage);
    }


}
