package chat.interfaces.state;

public interface ManagedState<T> extends SavesState<T>, RestoresState<T> {}
