package template.jwttemplate.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import template.jwttemplate.data.User;
import template.jwttemplate.dto.AuthenticationRequestDto;
import template.jwttemplate.dto.AuthenticationResponse;
import template.jwttemplate.dto.RegistrationRequest;
import template.jwttemplate.enums.Role;
import template.jwttemplate.exception.UserAlreadyExistsException;
import template.jwttemplate.repository.UserRepository;
import template.jwttemplate.security.JwtService;
import template.jwttemplate.security.UserDetailsServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService JwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

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
                .role(Role.USER)
                .build();

        repository.save(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                List.of(new SimpleGrantedAuthority(user.getRole().name())));

        var jwtToken = JwtService.generateToken(userDetails);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // If the credentials are correct \/
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        var jwtToken = JwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
