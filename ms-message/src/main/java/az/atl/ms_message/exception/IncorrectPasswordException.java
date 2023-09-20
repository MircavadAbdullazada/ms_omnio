package az.atl.ms_message.exception;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}