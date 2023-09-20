package az.atl.ms_auth.model;


import jakarta.persistence.Access;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {

    String oldPassword;
    String newPassword;
    String newPasswordAgain;

}
