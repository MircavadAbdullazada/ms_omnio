package az.atl.ms_auth.service.impl;

import az.atl.ms_auth.dao.entity.UserEntity;
import az.atl.ms_auth.dao.repository.UserRepository;
import az.atl.ms_auth.exception.*;
import az.atl.ms_auth.mapper.UserMapper;
import az.atl.ms_auth.model.Role;
import az.atl.ms_auth.model.UpdatePasswordRequest;
import az.atl.ms_auth.model.UpdateUserRequest;
import az.atl.ms_auth.model.consts.ExceptionMessages;
import az.atl.ms_auth.model.dto.UserDto;
import az.atl.ms_auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static az.atl.ms_auth.mapper.UserMapper.buildUserDto;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest, UserDetails userDetails) {
        String userName = userDetails.getUsername();
        logger.info("Updating password for user: {}", userName);

        // Check if the new passwords match
        if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getNewPasswordAgain())) {
            logger.error("New passwords do not match for user: {}", userName);
            throw new PasswordMismatchException(userName);
        }

        // Check if the old password is correct
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNameNotFoundException(userName));

        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            logger.error("Incorrect old password for user: {}", userName);
            throw new IncorrectPasswordException(userName);
        }

        // Encode the new password and update it in the database
        String encodedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        logger.info("Successfully updated password for user: {}", userName);
    }


    @Override
    public UserDto getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        return buildUserDto(userEntity);
    }


    @Override
    public UserDto getUserByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNameNotFoundException(userName));
        return buildUserDto(userEntity);
    }


    @Override
    public Page<UserDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAll(pageable);
        users.getContent();
        return users.map(UserMapper::buildUserDto);

    }

    @Override
    public UserDto updateUser(UpdateUserRequest updateUserRequest, UserDetails userDetails) {
        String userName = userDetails.getUsername();
        UserEntity currentUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNameNotFoundException(userName));

        currentUser.setFirstName(updateUserRequest.getFirstName());
        currentUser.setLastName(updateUserRequest.getLastName());
        currentUser.setUserName(updateUserRequest.getUserName());
        currentUser.setEmail(updateUserRequest.getEmail());
        currentUser.setJobTitle(updateUserRequest.getJobTitle());

        UserEntity updatedUser = userRepository.save(currentUser);
        return buildUserDto(updatedUser);
    }



    //For Admin
    @Override
    public void deleteUserById(Long id) {
            userRepository.deleteById(id);
    }


    public void deleteUser(String username) {
        userRepository.deleteByUserName(username);
    }



}

