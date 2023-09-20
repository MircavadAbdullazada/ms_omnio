package az.atl.ms_auth.service.impl;

import az.atl.ms_auth.dao.entity.LoginTable;
import az.atl.ms_auth.dao.entity.UserEntity;
import az.atl.ms_auth.dao.repository.LoginTableRepository;
import az.atl.ms_auth.dao.repository.UserRepository;
import az.atl.ms_auth.model.RegisterRequest;
import az.atl.ms_auth.model.RegisterResponse;
import az.atl.ms_auth.exception.UserNameExistsException;
import az.atl.ms_auth.model.AuthenticationRequest;
import az.atl.ms_auth.model.AuthenticationResponse;
import az.atl.ms_auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginTableRepository loginTableRepository;
    private MessageSource messageSource;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);




    public RegisterResponse register(RegisterRequest request) {
        logger.info("Received request to register user: {}", request.getUserName());

        var isExist = userRepository.findByUserName(request.getUserName()).isPresent();
        if (isExist) {
            logger.error("User with username {} already exists", request.getUserName());

        }

        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .jobTitle(request.getJobTitle())
                .role(request.getRole())
                .build();

        var userEntity = userRepository.save(user);

        logger.info("Successfully registered user: {}", request.getUserName());

        return RegisterResponse.buildRegisterDto(userEntity);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Received request to authenticate user: {}", request.getUserName());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new UserNameExistsException("Username exists with id:"));

        var jwtToken = jwtService.generateToken(user);

        LoginTable login = new LoginTable();
        login.setUserName(request.getUserName());
        login.setPassword(request.getPassword());
        login.setLoginDate(new Date());
        loginTableRepository.save(login);

        logger.info("Successfully authenticated user: {}", request.getUserName());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
