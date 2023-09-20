package az.atl.ms_auth.exception;

public class UserNameNotFoundException extends RuntimeException {
    public UserNameNotFoundException(String message) {
        super(message);
    }
}
