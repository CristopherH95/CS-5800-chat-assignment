package chat.interfaces.state;

public interface RestoresState<T> {
    void restore(T stateData);
}
