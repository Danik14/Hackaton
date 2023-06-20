package diploma.project.config;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import diploma.project.data.User;
import diploma.project.enums.UserRole;
import diploma.project.exception.UserNotFoundException;
import diploma.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner generateUsers() {
        return args -> {
            User user1 = new User(UUID.randomUUID(), UserRole.ADMIN, "John", "john@example.com",
                    passwordEncoder.encode("password1"));
            User user2 = new User(UUID.randomUUID(), UserRole.USER, "Jane", "jane@example.com",
                    passwordEncoder.encode("password2"));

            repository.save(user1);
            repository.save(user2);
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    // DAO responsible for fetching user details and encoding password
    @Bean
    public AuthenticationProvider authConfigProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
