package chat.interfaces.messages;

import chat.interfaces.server.ChatParticipant;

import java.util.Iterator;

public interface IterableByUser {
    Iterator<MessageData> userMessagesIterator(ChatParticipant user);
}
