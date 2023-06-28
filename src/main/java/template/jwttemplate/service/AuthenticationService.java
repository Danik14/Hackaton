package template.jwttemplate.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import template.jwttemplate.data.User;
import template.jwttemplate.data.VerificationToken;
import template.jwttemplate.dto.AuthenticationRequestDto;
import template.jwttemplate.dto.AuthenticationResponse;
import template.jwttemplate.dto.RegistrationRequest;
import template.jwttemplate.enums.UserRole;
import template.jwttemplate.exception.UserAlreadyExistsException;
import template.jwttemplate.repository.UserRepository;
import template.jwttemplate.security.JwtService;
import template.jwttemplate.security.UserDetailsServiceImpl;

@Service
@RequiredArgsConstructor
// @Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService JwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Value("${server.port}")
    private String serverPort;

    public AuthenticationResponse register(RegistrationRequest registrationRequest) {
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("User with such email already exists");
        } else if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new UserAlreadyExistsException("User with such username already exists");
        }

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .username(registrationRequest.getUsername())
                .role(UserRole.USER)
                .isActive(true)
                .build();

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);

        verificationTokenService.saveVerificationToken(verificationToken);

        // try {
        // String currentHost = InetAddress.getLocalHost().getHostAddress();
        String verificationLink = "http://localhost" + ":" + serverPort + "/api/v1/auth/verify?token="
                + verificationToken.getToken();
        emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), verificationLink);
        // } catch (UnknownHostException e) {
        // log.error("{}", e);
        // }

        UserDetails userDetails = userDetailsService.build(user);

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
