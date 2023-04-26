package chat.records;

import chat.interfaces.server.ChatParticipant;

import java.util.Set;

public record MessageRequest(ChatParticipant sender, String content, Set<String> recipientNames) {}
