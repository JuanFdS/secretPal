package com.tenPines.mailer;


import com.tenPines.builder.PersonBuilder;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PostOffice {

    public SMTPPostMan callThePostMan() throws IOException {
        Properties gmailProp = buildPropertyFrom("src/main/resources/gmail.properties");
        Properties mailProp = buildPropertyFrom("src/main/resources/mailTemplate.properties");
        return new SMTPPostMan(gmailProp, mailProp);
    }



    private Properties buildPropertyFrom(String route) throws IOException {
        Properties prop = new PropertyParser();
        prop.load(new FileInputStream(route));
        return prop;
    }



    public static void main(String[] args) throws IOException, MessagingException {
        Worker xx = new PersonBuilder().withEmail("rizziromanalejandro@gmail.com").build();
        Worker yy = new PersonBuilder().build();
        new PostOffice().callThePostMan().notifyPersonWithSecretPalInformation(xx, yy);

    }
}
