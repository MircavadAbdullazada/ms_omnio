package az.atl.ms_auth.exception;

public class UserNameExistsException extends RuntimeException {
    public UserNameExistsException(String message) {
        super(message);
    }
}
