package chat.interfaces.server;

import chat.interfaces.messages.MessageData;

public interface MessageReceiver {
    void receiveMessage(MessageData message);
}
