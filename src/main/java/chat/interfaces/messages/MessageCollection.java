package chat.interfaces.messages;

import chat.interfaces.server.ChatParticipant;

public interface MessageCollection extends IterableByUser {
    void addMessage(MessageData message);
    void removeMessage(MessageData message);
    MessageData getLastSent(ChatParticipant sender);
}
