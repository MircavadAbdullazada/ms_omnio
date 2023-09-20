package az.atl.ms_message.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message) {
        super(message);
    }
}
