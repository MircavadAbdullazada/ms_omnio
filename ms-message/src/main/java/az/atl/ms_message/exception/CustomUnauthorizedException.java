package az.atl.ms_message.exception;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message){
        super(message);
    }
}
