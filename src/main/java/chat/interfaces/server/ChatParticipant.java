package chat.interfaces.server;

import chat.interfaces.messages.IterableByUser;
import chat.interfaces.messages.MessageData;

public interface ChatParticipant extends MessageSender, MessageReceiver, IterableByUser {
    String getName();
    void setChatMediator(ChatMediator chatMediator);
    void undo();
    void receiveUndo(MessageData messageData);
}
