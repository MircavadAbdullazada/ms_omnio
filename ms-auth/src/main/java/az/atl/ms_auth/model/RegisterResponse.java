package az.atl.ms_auth.model;


import az.atl.ms_auth.dao.entity.UserEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {
    String firstName;
    String lastName;
    String userName;
    String password;
    String email;
    String jobTitle;
    Role role;

    public static RegisterResponse buildRegisterDto(UserEntity userEntity) {
        return RegisterResponse.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .jobTitle(userEntity.getJobTitle())
                .role(userEntity.getRole())
                .build();
    }
}
