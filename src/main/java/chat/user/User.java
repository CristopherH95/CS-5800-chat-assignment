package chat.user;

import chat.exceptions.MissingHistoryStateException;
import chat.interfaces.messages.MessageHistory;
import chat.interfaces.messages.MessageHistorySnapshot;
import chat.interfaces.server.ChatMediator;
import chat.interfaces.server.ChatParticipant;
import chat.interfaces.messages.MessageData;
import chat.records.MessageAddressing;
import chat.records.MessageRequest;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class User implements ChatParticipant {
    private final String name;
    private final MessageHistory messageHistory;
    private ChatMediator chatMediator;
    private MessageHistorySnapshot messageHistorySnapshot;

    public User(String name, ChatMediator chatMediator) {
        this.name = name;
        this.messageHistory = new ChatHistory();
        this.chatMediator = chatMediator;
        this.chatMediator.registerUser(this);
        this.messageHistorySnapshot = null;

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
    public void sendMessage(String content, Set<String> recipients) {
        MessageRequest messageRequest = new MessageRequest(this, content, recipients);
        messageHistorySnapshot = messageHistory.save();
        chatMediator.distributeMessage(messageRequest);
    }

    @Override
    public void receiveMessage(MessageData message) {
        if (checkShouldRemoveSnapshot(message)) {
            messageHistorySnapshot = null;
        }
        messageHistory.addMessage(message);
    }

    @Override
    public Iterator<MessageData> userMessagesIterator(ChatParticipant user) {
        return messageHistory.userMessagesIterator(user);
    }

    @Override
    public void undo() {
        if (Objects.isNull(messageHistorySnapshot)) {
            throw new MissingHistoryStateException("No message history state available!");
        }
        MessageData lastSent = messageHistory.getLastSent(this);
        messageHistory.restore(messageHistorySnapshot);
        messageHistorySnapshot = null;
        chatMediator.requestUndo(lastSent);
    }

    @Override
    public void receiveUndo(MessageData messageData) {
        messageHistory.removeMessage(messageData);
    }

    @Override
    public void block(String name) {
        chatMediator.block(this, name);
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

    // If receiving a message from another user, it is now too late
    // to hit 'undo' so we need to wipe out the snapshot.
    private boolean checkShouldRemoveSnapshot(MessageData messageData) {
        if (Objects.isNull(messageHistorySnapshot)) {
            return false;
        }
        MessageAddressing addressing = messageData.getAddressing();
        ChatParticipant sender = addressing.sender();

        return !sender.equals(this);
    }
}
