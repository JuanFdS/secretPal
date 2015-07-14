package mailer;

import javax.mail.Message;

public class DumbPostMan implements PostManService {

    @Override
    public Message createEmptyMessage() {
        return null;
    }

    @Override
    public void sendMessage(Message message) {

    }
}
