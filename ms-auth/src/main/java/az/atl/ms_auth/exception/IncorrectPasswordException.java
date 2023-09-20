package az.atl.ms_auth.exception;

public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException(String message) {
        super(message);
    }
}