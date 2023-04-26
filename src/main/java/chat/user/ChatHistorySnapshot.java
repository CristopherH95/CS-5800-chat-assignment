package chat.user;

import chat.interfaces.messages.MessageData;
import chat.interfaces.messages.MessageDataSnapshot;
import chat.interfaces.messages.MessageHistorySnapshot;
import chat.messages.MessageMemento;

import java.util.Iterator;
import java.util.TreeSet;

public class ChatHistorySnapshot implements MessageHistorySnapshot {
    private final TreeSet<MessageDataSnapshot> messageData;

    public ChatHistorySnapshot(Iterable<MessageData> messageData) {
        this.messageData = new TreeSet<>();
        for (MessageData data : messageData) {
            this.messageData.add(
                new MessageMemento(data)
            );
        }
    }

    @Override
    public Iterator<MessageDataSnapshot> iterator() {
        return messageData.iterator();
    }
}
