package chat.records;

import chat.interfaces.server.ChatParticipant;

import java.util.Set;

public record MessageAddressing(ChatParticipant sender, Set<ChatParticipant> recipients) {}
