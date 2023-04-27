package chat.server;

import chat.exceptions.UserNameConflictException;
import chat.exceptions.UserNotFoundException;
import chat.interfaces.server.ChatBlockCollection;
import chat.interfaces.server.ChatMediator;
import chat.interfaces.server.ChatParticipant;
import chat.interfaces.messages.MessageData;
import chat.messages.Message;
import chat.records.MessageAddressing;
import chat.records.MessageRequest;

import java.util.HashMap;
import java.util.HashSet;
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
        String userName = user.getName();

        if (users.containsKey(userName)) {
            throw new UserNameConflictException(String.format("User of name '%s' already exists", userName));
        }
        users.put(user.getName(), user);
    }

    @Override
    public void unRegisterUser(ChatParticipant user) {
        users.remove(user.getName());
        blockCollection.removeAllBlocks(user);
    }

    @Override
    public void block(ChatParticipant blockFrom, String blockToName) {
        ChatParticipant blockTo = getUserByName(blockToName);
        if (!Objects.isNull(blockTo)) {
            blockCollection.addBlock(blockFrom, blockTo);
        }
    }

    @Override
    public void unBlock(ChatParticipant blockFrom, String blockToName) {
        ChatParticipant blockTo = getUserByName(blockToName);
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
    public void distributeMessage(MessageRequest request) {
        MessageAddressing addressing = generateAddressing(request);
        MessageData message = new Message(addressing, request.content());

        for (ChatParticipant user : users.values()) {
            if (checkShouldReceiveMessage(user, message)) {
                user.receiveMessage(message);
            }
        }
    }

    private MessageAddressing generateAddressing(MessageRequest messageRequest) {
        ChatParticipant sender = messageRequest.sender();
        Set<ChatParticipant> recipients = new HashSet<>();

        for (String recipientName : messageRequest.recipientNames()) {
            recipients.add(getUserByName(recipientName));
        }

        return new MessageAddressing(
            sender,
            recipients
        );
    }

    private boolean checkShouldReceiveMessage(ChatParticipant receiver, MessageData message) {
        // messages are distributed to the original sender as well, so that the constructed message data is
        // added to their history.
        return (
            checkReceiverMatchesSender(receiver, message)
            ||
            (checkIsNotBlocked(receiver, message) && checkInRecipients(receiver, message))
        );
    }

    private boolean checkReceiverMatchesSender(ChatParticipant receiver, MessageData message) {
        MessageAddressing messageAddressing = message.getAddressing();
        ChatParticipant sender = messageAddressing.sender();

        return sender.equals(receiver);
    }

    private boolean checkIsNotBlocked(ChatParticipant receiver, MessageData message) {
        MessageAddressing messageAddressing = message.getAddressing();
        ChatParticipant sender = messageAddressing.sender();

        return !blockCollection.checkIsBlocked(receiver, sender);
    }

    private boolean checkInRecipients(ChatParticipant receiver, MessageData message) {
        MessageAddressing messageAddressing = message.getAddressing();
        Set<ChatParticipant> recipients = messageAddressing.recipients();

        return recipients.contains(receiver);
    }

    private ChatParticipant getUserByName(String name) {
        ChatParticipant user = users.get(name);
        if (Objects.isNull(user)) {
            throw new UserNotFoundException(String.format("Unable to find user '%s'", name));
        }
        return user;
    }
}
