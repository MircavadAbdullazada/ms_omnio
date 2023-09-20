package az.atl.ms_auth.controller;

import az.atl.ms_auth.model.RegisterRequest;
import az.atl.ms_auth.model.RegisterResponse;
import az.atl.ms_auth.model.AuthenticationRequest;
import az.atl.ms_auth.model.AuthenticationResponse;
import az.atl.ms_auth.service.impl.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register( @RequestBody @Valid  RegisterRequest request) {
         return ResponseEntity.ok(authenticationService.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
