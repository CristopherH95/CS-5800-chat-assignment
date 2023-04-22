package chat.user;

import chat.interfaces.ChatMediator;
import chat.interfaces.ChatParticipant;
import chat.interfaces.MessageCollection;
import chat.interfaces.MessageData;
import chat.messages.Message;
import chat.messages.MessageAddressing;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class User implements ChatParticipant {
    private final String name;
    private ChatMediator chatMediator;
    private final MessageCollection messageHistory;

    public User(String name, ChatMediator chatMediator) {
        this.name = name;
        this.messageHistory = new ChatHistory();
        this.chatMediator = chatMediator;
        this.chatMediator.registerUser(this);
    }

    @Override
    public String getName() {
        return name;
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
    public void receiveMessage(MessageData message) {
        messageHistory.addMessage(message);
    }

    @Override
    public String toString() {
        return String.format("[User: '%s']", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public Iterator<MessageData> userMessagesIterator(ChatParticipant user) {
        return messageHistory.userMessagesIterator(user);
    }
}
