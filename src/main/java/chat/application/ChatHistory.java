package chat.application;

import chat.exceptions.MessageHistoryNotFoundException;
import chat.interfaces.ChatParticipant;
import chat.interfaces.MessageCollection;
import chat.interfaces.MessageInformation;

import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

public class ChatHistory implements MessageCollection {
    private final TreeSet<MessageInformation> messages;

    public ChatHistory() {
        messages = new TreeSet<>();
    }

    @Override
    public void addMessage(MessageInformation message) {
        messages.add(message);
    }

    @Override
    public MessageInformation getLastSent(ChatParticipant sender) {
        MessageInformation messageFound = null;
        Iterator<MessageInformation> iterator = messages.descendingIterator();

        while (iterator.hasNext()) {
            MessageInformation message = iterator.next();
            if (message.getSenderID() == sender.getID()) {
                messageFound = message;
                break;
            }
        }

        if (Objects.isNull(messageFound)) {
            throw new MessageHistoryNotFoundException(String.format("No last message sent for %s", sender));
        }

        return messageFound;
    }
}
