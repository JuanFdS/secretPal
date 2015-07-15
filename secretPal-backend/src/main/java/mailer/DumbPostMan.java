package mailer;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class DumbPostMan implements PostManService {

    private List<Message> messages = new ArrayList<>();

    @Override
    public Message createEmptyMessage() {
        return new MimeMessage(mock(Session.class));
    }

    @Override
    public void sendMessage(Message message) {
        messages.add(message);
    }

    public int getEmailCount() {
        return messages.size();
    }

    public Message getMessage() {
        return messages.get(0);
    }
}
