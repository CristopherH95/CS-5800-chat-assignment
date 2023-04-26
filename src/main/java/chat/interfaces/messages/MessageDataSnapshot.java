package chat.interfaces.messages;

import java.util.Date;
import java.util.UUID;

public interface MessageDataSnapshot extends Comparable<MessageDataSnapshot> {
    MessageData regenerate();
    String getContent();
    Date getTimestamp();
    UUID getMessageID();
}
