package chat.exceptions;

public class MissingHistoryStateException extends RuntimeException {
    public MissingHistoryStateException(String message) {
        super(message);
    }
}
