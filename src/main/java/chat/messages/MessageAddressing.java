package chat.messages;

import chat.interfaces.ChatParticipant;

import java.util.Set;

public record MessageAddressing(ChatParticipant sender, Set<ChatParticipant> recipients) {}
