package chat.application;

import chat.interfaces.MessageInformation;
import chat.records.MessageAddressing;

import java.util.Date;

public class Message implements MessageInformation {
    private final MessageAddressing addressing;
    private final String content;
    private final Date timestamp;

    public Message(MessageAddressing addressing, String content) {
        this.addressing = addressing;
        this.content = content;
        this.timestamp = new Date();
    }

    public long getSenderID() {
        return addressing.sender().getID();
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
    public int compareTo(MessageInformation otherMessage) {
        return timestamp.compareTo(otherMessage.getTimestamp());
    }
}
