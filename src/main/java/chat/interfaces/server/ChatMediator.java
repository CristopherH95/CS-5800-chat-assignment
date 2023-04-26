package chat.interfaces.server;

import chat.interfaces.messages.MessageData;
import chat.records.MessageRequest;

public interface ChatMediator {
    void registerUser(ChatParticipant user);
    void unRegisterUser(ChatParticipant user);
    void block(ChatParticipant blockFrom, String blockToName);
    void unBlock(ChatParticipant blockFrom, String blockToName);
    void requestUndo(MessageData messageData);
    void distributeMessage(MessageRequest request);
}
