package az.atl.ms_auth.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String firstName;
    String lastName;
    String userName;
    String email;
    String jobTitle;
}
