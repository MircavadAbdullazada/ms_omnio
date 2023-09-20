package az.atl.ms_message.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
