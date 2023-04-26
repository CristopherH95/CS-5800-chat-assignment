package chat.interfaces.messages;

import chat.messages.MessageAddressing;

import java.util.Date;
import java.util.UUID;

public interface MessageData extends Comparable<MessageData> {
    MessageAddressing getAddressing();
    String getContent();
    Date getTimestamp();
    UUID getMessageID();
}
