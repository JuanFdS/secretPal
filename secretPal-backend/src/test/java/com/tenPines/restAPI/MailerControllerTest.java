package com.tenPines.restAPI;


import com.tenPines.application.SecretPalSystem;
import com.tenPines.mailer.DumbPostMan;
import com.tenPines.model.Worker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class MailerControllerTest {
    Worker friendWorker;
    Worker birthdayWorker;
    DumbPostMan dumbPostMan;
    private SecretPalSystem secretPalSystem;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws IOException {
        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");
        new File("/tmp/emailTemplate.properties").createNewFile();
        secretPalSystem.mailTemplateProperties = "/tmp/emailTemplate.properties";
    }

    @Test
    public void When_I_Change_The_Mail_Body_It_Should_Change() throws IOException {
        secretPalSystem.setEMailTemplate("Subject", "Body");

        assertThat(secretPalSystem.getEMailTemplate().getProperty("mail.subject"), is("Subject"));
        assertThat(secretPalSystem.getEMailTemplate().getProperty("mail.bodyText"), is("Body"));
    }

    @Test
    public void When_I_Disable_The_Mailing_Services_It_Should_Change() throws IOException {
        secretPalSystem.changeMailSendingFunctions(false);

        assertThat(secretPalSystem.getSafePostMan(), is(instanceOf(DumbPostMan.class)));
    }

}