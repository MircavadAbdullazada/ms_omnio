package az.atl.ms_auth.controller;

import az.atl.ms_auth.model.UpdatePasswordRequest;
import az.atl.ms_auth.model.UpdateUserRequest;
import az.atl.ms_auth.model.dto.UserDto;
import az.atl.ms_auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    //for Admin
    @GetMapping("/admin/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("/get/{userName}")
    public ResponseEntity<UserDto> getUserByUserName(@PathVariable String userName){
       UserDto userDto= userService.getUserByUserName(userName);
       return ResponseEntity.ok(userDto);
    }

    //for Admin
    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<UserDto> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest,
                               @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Received request to update password for user: {}", userDetails.getUsername());
        userService.updatePassword(updatePasswordRequest, userDetails);
        logger.info("Successfully updated password for user: {}", userDetails.getUsername());
    }


    @PutMapping("/updateUser/{userName}")
        public void updateUser( @RequestBody UpdateUserRequest updateUserRequest ,
                                @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateUser(updateUserRequest, userDetails);
    }

    //for Admin
    @DeleteMapping("/admin/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    @DeleteMapping("/current")
    public ResponseEntity<String> deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }




}




