package chat.interfaces.server;

import java.util.Set;

public interface MessageSender {
    void sendMessage(String content, Set<ChatParticipant> recipients);
}
