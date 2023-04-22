package chat.server;

import chat.interfaces.ChatBlockCollection;
import chat.interfaces.ChatMediator;
import chat.interfaces.ChatParticipant;
import chat.interfaces.MessageData;
import chat.messages.MessageAddressing;

import java.util.HashSet;
import java.util.Set;

public class ChatServer implements ChatMediator {
    private final ChatBlockCollection blockCollection;
    private final HashSet<ChatParticipant> users;

    public ChatServer() {
        blockCollection = new ChatBlockHistory();
        users = new HashSet<>();
    }

    @Override
    public void registerUser(ChatParticipant user) {
        users.add(user);
    }

    @Override
    public void unRegisterUser(ChatParticipant user) {
        users.remove(user);
        blockCollection.removeAllBlocks(user);
    }

    @Override
    public void block(ChatParticipant blockFrom, ChatParticipant blockTo) {
        blockCollection.addBlock(blockFrom, blockTo);
    }

    @Override
    public void unBlock(ChatParticipant blockFrom, ChatParticipant blockTo) {
        blockCollection.removeBlock(blockFrom, blockTo);
    }

    @Override
    public void distributeMessage(MessageData message) {
        for (ChatParticipant user : users) {
            if (checkShouldReceiveMessage(user, message)) {
                user.receiveMessage(message);
            }
        }
    }

    private boolean checkShouldReceiveMessage(ChatParticipant receiver, MessageData message) {
        MessageAddressing messageAddressing = message.getAddressing();
        ChatParticipant sender = messageAddressing.sender();
        Set<ChatParticipant> recipients = messageAddressing.recipients();

        return !blockCollection.checkIsBlocked(receiver, sender) && recipients.contains(receiver);
    }
}
