package chat.messages;

import chat.interfaces.messages.MessageData;
import chat.interfaces.messages.MessageDataSnapshot;
import chat.records.MessageAddressing;

import java.util.Date;
import java.util.UUID;

public class MessageMemento implements MessageDataSnapshot {
    private final MessageAddressing originalAddressing;
    private final String originalContent;
    private final Date originalTimestamp;
    private final UUID originalID;

    public MessageMemento(MessageData messageData) {
        this.originalAddressing = messageData.getAddressing();
        this.originalContent = messageData.getContent();
        this.originalTimestamp = messageData.getTimestamp();
        this.originalID = messageData.getMessageID();
    }

    public Message regenerate() {
        return new Message(
            originalAddressing,
            originalContent,
            originalTimestamp
        );
    }

    @Override
    public String getContent() {
        return originalContent;
    }

    @Override
    public Date getTimestamp() {
        return originalTimestamp;
    }

    @Override
    public UUID getMessageID() {
        return originalID;
    }

    @Override
    public int compareTo(MessageDataSnapshot o) {
        return originalTimestamp.compareTo(o.getTimestamp());
    }
}
