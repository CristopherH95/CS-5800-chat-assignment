package chat.interfaces;

import java.util.Iterator;

public interface IterableByUser {
    Iterator<MessageData> userMessagesIterator(ChatParticipant user);
}
