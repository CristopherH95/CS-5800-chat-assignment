package chat.interfaces;

public interface ChatMediator {
    void registerUser(ChatParticipant user);
    void unRegisterUser(ChatParticipant user);
    void block(ChatParticipant blockFrom, ChatParticipant blockTo);
    void unBlock(ChatParticipant blockFrom, ChatParticipant blockTo);
    void distributeMessage(MessageData message);
}
