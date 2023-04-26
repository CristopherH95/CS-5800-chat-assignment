package chat.user;

import chat.exceptions.MessageHistoryNotFoundException;
import chat.interfaces.messages.MessageDataSnapshot;
import chat.interfaces.messages.MessageHistory;
import chat.interfaces.messages.MessageHistorySnapshot;
import chat.interfaces.server.ChatParticipant;
import chat.interfaces.messages.MessageData;
import chat.messages.MessageAddressing;

import java.util.*;
import java.util.function.Predicate;

public class ChatHistory implements MessageHistory {
    private final TreeSet<MessageData> messages;

    public ChatHistory() {
        messages = new TreeSet<>();
    }

    @Override
    public MessageHistorySnapshot save() {
        return new ChatHistorySnapshot(this.messages);
    }

    @Override
    public void restore(MessageHistorySnapshot snapshot) {
        messages.clear();
        for (MessageDataSnapshot memento : snapshot) {
            messages.add(memento.regenerate());
        }
    }

    @Override
    public void addMessage(MessageData message) {
        messages.add(message);
    }

    public void removeMessage(MessageData message) {
        UUID toDeleteID = message.getMessageID();

        for (MessageData messageData : messages) {
            UUID messageID = messageData.getMessageID();

            if (messageID.equals(toDeleteID)) {
                messages.remove(messageData);
                break;
            }
        }
    }

    @Override
    public MessageData getLastSent(ChatParticipant sender) {
        MessageData messageFound = null;
        Iterator<MessageData> iterator = messages.descendingIterator();

        while (iterator.hasNext()) {
            MessageData message = iterator.next();
            if (checkMessageSenderMatches(message, sender)) {
                messageFound = message;
                break;
            }
        }

        if (Objects.isNull(messageFound)) {
            throw new MessageHistoryNotFoundException(String.format("No last message sent for %s", sender));
        }

        return messageFound;
    }

    @Override
    public Iterator<MessageData> userMessagesIterator(ChatParticipant user) {
        Predicate<MessageData> userPredicate = message -> {
            MessageAddressing addressing = message.getAddressing();
            ChatParticipant originalSender = addressing.sender();
            Set<ChatParticipant> recipients = addressing.recipients();

            return originalSender.equals(user) || recipients.contains(user);
        };

        return messages.stream()
                .filter(userPredicate)
                .iterator();
    }

    private boolean checkMessageSenderMatches(MessageData messageData, ChatParticipant sender) {
        MessageAddressing addressing = messageData.getAddressing();
        ChatParticipant originalSender = addressing.sender();

        return originalSender.equals(sender);
    }
}
