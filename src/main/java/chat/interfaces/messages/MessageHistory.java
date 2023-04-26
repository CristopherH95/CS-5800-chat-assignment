package chat.interfaces.messages;

import chat.interfaces.state.ManagedState;

public interface MessageHistory extends MessageCollection, ManagedState<MessageHistorySnapshot> {}
