package chat.messages;

import chat.interfaces.messages.MessageData;
import chat.interfaces.server.ChatParticipant;
import chat.records.MessageAddressing;

import java.util.Date;
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

    @Override
    public String toString() {
        ChatParticipant sender = addressing.sender();

        return String.format("%s (%s): %s", sender, timestamp, content);
    }
}
