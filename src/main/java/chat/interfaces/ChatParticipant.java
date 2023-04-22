package chat.interfaces;

public interface ChatParticipant extends MessageSender, MessageReceiver, IterableByUser {
    String getName();
    void setChatMediator(ChatMediator chatMediator);
}
