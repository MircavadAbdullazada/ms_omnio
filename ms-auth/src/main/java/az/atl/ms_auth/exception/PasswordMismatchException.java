package az.atl.ms_auth.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message) {
        super(message);
    }
}
