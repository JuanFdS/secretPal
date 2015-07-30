package com.tenPines.mailer;


import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PostOffice {

    public SMTPPostMan callThePostMan() throws IOException {
        Properties gmailProp = new PropertyParser("src/main/resources/gmail.properties");
        Properties mailProp = new PropertyParser("src/main/resources/mailTemplate.properties");
        return new SMTPPostMan(gmailProp, mailProp);
    }

}
