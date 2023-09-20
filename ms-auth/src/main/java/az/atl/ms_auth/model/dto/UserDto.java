package az.atl.ms_auth.model.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    String firstName;
    String lastName;
    String userName;
    String password;
    String email;
    String jobTitle;
}
