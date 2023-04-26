package chat.exceptions;

public class UserNameConflictException extends RuntimeException {
    public UserNameConflictException(String message) {
        super(message);
    }
}
