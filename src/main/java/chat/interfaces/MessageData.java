package chat.interfaces;

import chat.messages.MessageAddressing;

import java.util.Date;

public interface MessageData extends Comparable<MessageData> {
    MessageAddressing getAddressing();
    String getContent();
    Date getTimestamp();
}
