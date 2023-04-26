package chat.interfaces.server;

import chat.interfaces.messages.MessageData;

public interface ChatMediator {
    void registerUser(ChatParticipant user);
    void unRegisterUser(ChatParticipant user);
    void block(ChatParticipant blockFrom, ChatParticipant blockTo);
    void unBlock(ChatParticipant blockFrom, ChatParticipant blockTo);
    void requestUndo(MessageData messageData);
    void distributeMessage(MessageData message);
}
