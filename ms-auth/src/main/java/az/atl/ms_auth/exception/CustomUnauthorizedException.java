package az.atl.ms_auth.exception;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message){
        super(message);
    }
}
