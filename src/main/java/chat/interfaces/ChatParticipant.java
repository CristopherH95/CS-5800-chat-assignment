package chat.interfaces;

public interface ChatParticipant extends MessageSender, MessageReceiver {
    long getID();
    String getName();
    void setName(String name);
    void setChatMediator(ChatMediator chatMediator);
}
