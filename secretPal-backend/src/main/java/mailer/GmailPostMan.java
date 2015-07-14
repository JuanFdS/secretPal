package mailer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailPostMan implements PostManService{

    private final String username = "cumples@10pines.com";
    private final String password = "pinoscumpleanieros";


    private Properties getGMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private Session getAuthenticatedSession() {
        Session session = Session.getInstance(getGMailProperties(),
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        return session;
    }

    public Message createEmptyMessage() {
        return new MimeMessage(getAuthenticatedSession());
    }

    public void sendMessage(Message message) {
        try{
            message.setFrom(new InternetAddress(username));
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}