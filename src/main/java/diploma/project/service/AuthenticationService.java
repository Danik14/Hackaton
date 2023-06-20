package diploma.project.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import diploma.project.data.User;
import diploma.project.dto.AuthenticationRequest;
import diploma.project.dto.AuthenticationResponse;
import diploma.project.dto.RegistrationRequest;
import diploma.project.exception.UserAlreadyExistsException;
import diploma.project.exception.UserNotFoundException;
import diploma.project.repository.UserRepository;
import diploma.project.security.JwtService;
import lombok.RequiredArgsConstructor;

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
