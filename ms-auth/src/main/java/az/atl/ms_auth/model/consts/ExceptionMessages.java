package az.atl.ms_auth.model.consts;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessages {
    USER_NOT_FOUND("user.notFound"),USERNAME_NOT_FOUND("userName.notFound"), USERNAME_EXISTS("userName.exists")
    , PASSWORD_MISMATCH("userPassword.mismatch"),PASSWORD_INCORRECT("userPassword.incorrect"),
    UNAUTHORIZED_EXCEPTION("unauthorized.exception"),MESSAGE_NOT_FOUND("message.notFound");

    private final String message;

}
