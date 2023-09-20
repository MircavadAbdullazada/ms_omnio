package az.atl.ms_auth.service;

import az.atl.ms_auth.dao.entity.UserEntity;
import az.atl.ms_auth.dao.repository.UserRepository;
import az.atl.ms_auth.exception.UserNameNotFoundException;
import az.atl.ms_auth.exception.UserNotFoundException;
import az.atl.ms_auth.model.Role;
import az.atl.ms_auth.model.UpdatePasswordRequest;
import az.atl.ms_auth.model.UpdateUserRequest;
import az.atl.ms_auth.model.dto.UserDto;
import az.atl.ms_auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetails userDetails;

    @Mock
    private PasswordEncoder passwordEncoder;

    @MockBean
    private MessageSource messageSource;


    private UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(passwordEncoder, messageSource, userRepository);
    }


    @Test
    void testUpdatePassword_Success() {
        // Given
        String userName = "user";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";




        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.setOldPassword(oldPassword);
        updatePasswordRequest.setNewPassword(newPassword);
        updatePasswordRequest.setNewPasswordAgain(newPassword);

        UserDetails userDetails = new User(userName, oldPassword, Collections.emptyList());

        UserEntity user = new UserEntity();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(oldPassword)); // Encode old password

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, user.getPassword())).thenReturn(true);


        // When and Then
        assertAll("Password update success",
                () -> {
                    assertDoesNotThrow(() -> userService.updatePassword(updatePasswordRequest, userDetails));
                    verify(userRepository, times(1)).findByUserName(userName);
                    verify(passwordEncoder, times(1)).matches(oldPassword, user.getPassword());
                    verify(userRepository, times(1)).save(user);
                }
        );
    }


    @Test
    void getUserById() {
        //Given
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName",
                "UserName", "password", "user@gmail.com", "Job Title", Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        //When
        UserDto result = userService.getUserById(1L);

        //Then
        assertAll(
                () -> assertEquals("FirstName", result.getFirstName()),
                () -> assertEquals("LastName", result.getLastName()),
                () -> assertEquals("UserName", result.getUserName())
        );


    }

    @Test
    void getAllUsers() {
        //Given
        int page = 0;
        int size = 10;

        List<UserEntity> userEntities = List.of(
                new UserEntity(1L, "FirstName1", "LastName1",
                        "UserName", "password1", "user1@gmail.com", "JobTitle1", Role.ADMIN),
                new UserEntity(2L, "FirstName2", "LastName2",
                        "UserName1", "password2", "user2@gmail.com", "JobTitle2", Role.ADMIN)
        );
        Page<UserEntity> mockUserPage = new PageImpl<>(userEntities);

        when(userRepository.findAll(PageRequest.of(page, size))).thenReturn(mockUserPage);

        //When
        Page<UserDto> result = userService.getAllUsers(page, size);

        //Then
        assertEquals(mockUserPage.getTotalElements(), result.getTotalElements());

    }

    @Test
    void testUpdateUser_UserFound() {
        // Given
        String userName = "userName";
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName",
                "UserName", "password", "user@gmail.com", "Job Title", Role.ADMIN);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("UpdatedFirstName", "UpdatedLastName",
                "UserName", "email", "jobTitle");

        UserDetails userDetails = User.withUsername(userName).password("password").roles("USER").build();


        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setUserName(userName);


        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        UserDto result = userService.updateUser(updateUserRequest, userDetails);


        // Then
        assertEquals("UpdatedFirstName", result.getFirstName());
        assertEquals("UpdatedLastName", result.getLastName());

    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Given
        String userName = "userName";
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("FirstName", "LastName",
                "UserName", "email", "jobTitle");

        UserDetails userDetails = User.withUsername(userName).password("password").roles("USER").build();

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNameNotFoundException.class, () -> userService.updateUser(updateUserRequest, userDetails));
    }


}


