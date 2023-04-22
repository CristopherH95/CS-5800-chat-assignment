package chat.interfaces;

import java.util.Date;

public interface MessageArchive {
    int getMessageHashCode();
    String getContent();
    Date getTimestamp();
}
