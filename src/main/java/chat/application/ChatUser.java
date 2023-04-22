package chat.application;

import chat.interfaces.ChatMediator;
import chat.interfaces.ChatParticipant;
import chat.interfaces.MessageCollection;
import chat.interfaces.MessageInformation;
import chat.records.MessageAddressing;

import java.util.Set;

public class ChatUser implements ChatParticipant {
    private static long userCounter = 0;
    private final long ID;
    private String name;
    private ChatMediator chatMediator;
    private final MessageCollection messageHistory;

    public ChatUser(String name, ChatMediator chatMediator) {
        userCounter++;
        this.ID = userCounter;
        this.name = name;
        this.messageHistory = new ChatHistory();
        this.chatMediator = chatMediator;
        this.chatMediator.registerUser(this);
    }

    @Override
    public long getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChatMediator(ChatMediator chatMediator) {
        this.chatMediator.unRegisterUser(this);
        this.chatMediator = chatMediator;
        this.chatMediator.registerUser(this);
    }

    @Override
    public void sendMessage(String content, Set<ChatParticipant> recipients) {
        MessageAddressing addressing = new MessageAddressing(this, recipients);
        Message message = new Message(addressing, content);
        chatMediator.distributeMessage(message);
    }

    @Override
    public void receiveMessage(MessageInformation message) {
        messageHistory.addMessage(message);
    }

    @Override
    public String toString() {
        return String.format("[User: '%s']", name);
    }
}
