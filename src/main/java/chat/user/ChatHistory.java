package chat.user;

import chat.exceptions.MessageHistoryNotFoundException;
import chat.interfaces.ChatParticipant;
import chat.interfaces.MessageCollection;
import chat.interfaces.MessageData;
import chat.messages.MessageAddressing;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

public class ChatHistory implements MessageCollection {
    private final TreeSet<MessageData> messages;

    public ChatHistory() {
        messages = new TreeSet<>();
    }

    @Override
    public void addMessage(MessageData message) {
        messages.add(message);
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
