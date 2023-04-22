package chat.server;

import chat.interfaces.ChatBlockCollection;
import chat.interfaces.ChatParticipant;

import java.util.HashMap;
import java.util.HashSet;

public class ChatBlockHistory implements ChatBlockCollection {
    private final HashMap<ChatParticipant, HashSet<ChatParticipant>> blocks;

    public ChatBlockHistory() {
        blocks = new HashMap<>();
    }

    @Override
    public void addBlock(ChatParticipant blockFrom, ChatParticipant blockTo) {
        if (!blocks.containsKey(blockFrom)) {
            blocks.put(blockFrom, new HashSet<>());
        }
        HashSet<ChatParticipant> userBlocks = blocks.get(blockFrom);
        userBlocks.add(blockTo);
    }

    @Override
    public void removeBlock(ChatParticipant blockFrom, ChatParticipant blockTo) {
        if (!blocks.containsKey(blockFrom)) {
            return;
        }
        HashSet<ChatParticipant> userBlocks = blocks.get(blockFrom);
        userBlocks.remove(blockTo);
    }

    @Override
    public void removeAllBlocks(ChatParticipant blockFrom) {
        blocks.remove(blockFrom);
    }

    @Override
    public boolean checkIsBlocked(ChatParticipant blockFrom, ChatParticipant blockTo) {
        if (!blocks.containsKey(blockFrom)) {
            return false;
        }
        HashSet<ChatParticipant> userBlocks = blocks.get(blockFrom);
        return userBlocks.contains(blockTo);
    }
}
