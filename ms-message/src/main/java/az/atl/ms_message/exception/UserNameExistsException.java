package az.atl.ms_message.exception;

public class UserNameExistsException extends RuntimeException {
    public UserNameExistsException(String message) {
        super(message);
    }
}
