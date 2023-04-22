package chat.interfaces;

import chat.records.MessageAddressing;

import java.util.Date;

public interface MessageInformation extends Comparable<MessageInformation> {
    long getSenderID();
    MessageAddressing getAddressing();
    String getContent();
    Date getTimestamp();
}
