package az.atl.ms_auth.service;

import az.atl.ms_auth.model.RegisterRequest;
import az.atl.ms_auth.model.RegisterResponse;
import az.atl.ms_auth.model.AuthenticationRequest;
import az.atl.ms_auth.model.AuthenticationResponse;

public interface AuthenticationService {


        RegisterResponse register(RegisterRequest request);
        AuthenticationResponse authenticate(AuthenticationRequest request);


}
