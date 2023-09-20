package az.atl.ms_auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotBlank(message = "First name is mandatory")
    String firstName;

    @NotBlank(message = "Last name is mandatory")
    String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, message = "Username must be at least 4 characters long")
    String userName;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9.]+)$", message = "Password must contain both letters and numbers")
    String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    String email;

    String jobTitle;
    Role role;

}
