package mailer;

import javax.mail.Message;

public interface PostManService {

    Message createEmptyMessage();

    void sendMessage(Message message);
}
