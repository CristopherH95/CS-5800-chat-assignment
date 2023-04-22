package chat.exceptions;

public class MessageHistoryNotFoundException extends RuntimeException {
    public MessageHistoryNotFoundException(String message) {
        super(message);
    }
}
