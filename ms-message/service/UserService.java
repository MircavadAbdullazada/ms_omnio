package az.atl.ms_auth.service;

import az.atl.ms_auth.model.UpdateUserRequest;
import az.atl.ms_auth.model.dto.UserDto;
import az.atl.ms_auth.model.UpdatePasswordRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void updatePassword(UpdatePasswordRequest updatePasswordRequest, UserDetails userDetails);
    UserDto getUserById(Long userId);
    Page<UserDto> getAllUsers(int page, int size);
    UserDto updateUser (UpdateUserRequest updateUserRequest, UserDetails userDetails);
    void deleteUserById(Long id );

    void deleteUser(String username);

    UserDto getUserByUserName(String userName);


}
