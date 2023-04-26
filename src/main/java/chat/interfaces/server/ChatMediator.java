package chat.interfaces.server;

import chat.interfaces.messages.MessageData;

public interface ChatMediator {
    void registerUser(ChatParticipant user);
    void unRegisterUser(ChatParticipant user);
    void block(ChatParticipant blockFrom, String blockToName);
    void unBlock(ChatParticipant blockFrom, String blockToName);
    void requestUndo(MessageData messageData);
    void distributeMessage(MessageData message);
}
