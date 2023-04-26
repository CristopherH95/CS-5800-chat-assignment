package chat.server;

import chat.interfaces.server.ChatBlockCollection;
import chat.interfaces.server.ChatMediator;
import chat.interfaces.server.ChatParticipant;
import chat.interfaces.messages.MessageData;
import chat.messages.MessageAddressing;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class ChatServer implements ChatMediator {
    private final ChatBlockCollection blockCollection;
    private final HashMap<String, ChatParticipant> users;

    public ChatServer() {
        blockCollection = new ChatBlockHistory();
        users = new HashMap<>();
    }

    @Override
    public void registerUser(ChatParticipant user) {
        users.put(user.getName(), user);
    }

    @Override
    public void unRegisterUser(ChatParticipant user) {
        users.remove(user.getName());
        blockCollection.removeAllBlocks(user);
    }

    @Override
    public void block(ChatParticipant blockFrom, String blockToName) {
        ChatParticipant blockTo = users.get(blockToName);
        if (!Objects.isNull(blockTo)) {
            blockCollection.addBlock(blockFrom, blockTo);
        }
    }

    @Override
    public void unBlock(ChatParticipant blockFrom, String blockToName) {
        ChatParticipant blockTo = users.get(blockToName);
        if (!Objects.isNull(blockTo)) {
            blockCollection.removeBlock(blockFrom, blockTo);
        }
    }

    @Override
    public void requestUndo(MessageData messageData) {
        for (ChatParticipant user : users.values()) {
            if (checkInRecipients(user, messageData)) {
                user.receiveUndo(messageData);
            }
        }
    }

    @Override
    public void distributeMessage(MessageData message) {
        for (ChatParticipant user : users.values()) {
            if (checkShouldReceiveMessage(user, message)) {
                user.receiveMessage(message);
            }
        }
    }

    private boolean checkShouldReceiveMessage(ChatParticipant receiver, MessageData message) {
        MessageAddressing messageAddressing = message.getAddressing();
        ChatParticipant sender = messageAddressing.sender();

        return !blockCollection.checkIsBlocked(receiver, sender) && checkInRecipients(receiver, message);
    }

    private boolean checkInRecipients(ChatParticipant receiver, MessageData message) {
        MessageAddressing messageAddressing = message.getAddressing();
        Set<ChatParticipant> recipients = messageAddressing.recipients();

        return recipients.contains(receiver);
    }
}
