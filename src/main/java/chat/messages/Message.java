package chat.messages;

import chat.interfaces.messages.MessageData;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Message implements MessageData {
    private final MessageAddressing addressing;
    private final String content;
    private final Date timestamp;
    private final UUID messageID;

    public Message(MessageAddressing addressing, String content) {
        this.addressing = addressing;
        this.content = content;
        this.timestamp = new Date();
        this.messageID = UUID.randomUUID();
    }

    public Message(MessageAddressing addressing, String content, Date timestamp) {
        this.addressing = addressing;
        this.content = content;
        this.timestamp = timestamp;
        this.messageID = UUID.randomUUID();
    }

    public MessageAddressing getAddressing() {
        return addressing;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public UUID getMessageID() {
        return messageID;
    }

    @Override
    public int compareTo(MessageData otherMessage) {
        return timestamp.compareTo(otherMessage.getTimestamp());
    }
}
