package template.jwttemplate.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import template.jwttemplate.data.User;
import template.jwttemplate.dto.AuthenticationRequest;
import template.jwttemplate.dto.AuthenticationResponse;
import template.jwttemplate.dto.RegistrationRequest;
import template.jwttemplate.exception.UserAlreadyExistsException;
import template.jwttemplate.exception.UserNotFoundException;
import template.jwttemplate.repository.UserRepository;
import template.jwttemplate.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService JwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        if (repository.existsByEmail(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with such email already exists");
        } else if (repository.existsByUsername(registrationRequest.getUsername())) {
            throw new UserAlreadyExistsException("User with such username already exists");
        }

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .username(registrationRequest.getUsername())
                .build();

        repository.save(user);

        var jwtToken = JwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // If the credentials are correct \/
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User with such email not found"));

        var jwtToken = JwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
