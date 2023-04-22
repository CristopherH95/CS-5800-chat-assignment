package chat.interfaces;

public interface MessageCollection {
    void addMessage(MessageInformation message);
    MessageInformation getLastSent(ChatParticipant sender);
}
