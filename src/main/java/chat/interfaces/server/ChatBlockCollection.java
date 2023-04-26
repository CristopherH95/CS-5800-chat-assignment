package chat.interfaces.server;

public interface ChatBlockCollection {
    void addBlock(ChatParticipant blockFrom, ChatParticipant blockTo);
    void removeBlock(ChatParticipant blockFrom, ChatParticipant blockTo);
    void removeAllBlocks(ChatParticipant blockFrom);
    boolean checkIsBlocked(ChatParticipant blockFrom, ChatParticipant blockTo);
}
