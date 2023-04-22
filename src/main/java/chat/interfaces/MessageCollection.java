package chat.interfaces;

public interface MessageCollection extends IterableByUser {
    void addMessage(MessageData message);
    MessageData getLastSent(ChatParticipant sender);
}
