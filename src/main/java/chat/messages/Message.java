package chat.messages;

import chat.interfaces.MessageData;

import java.util.Date;

public class Message implements MessageData {
    private final MessageAddressing addressing;
    private final String content;
    private final Date timestamp;

    public Message(MessageAddressing addressing, String content) {
        this.addressing = addressing;
        this.content = content;
        this.timestamp = new Date();
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
    public int compareTo(MessageData otherMessage) {
        return timestamp.compareTo(otherMessage.getTimestamp());
    }
}
