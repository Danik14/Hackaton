package diploma.project.config;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import diploma.project.data.User;
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
            User user1 = new User(UUID.randomUUID(), "John", "john@example.com", passwordEncoder.encode("password1"));
            User user2 = new User(UUID.randomUUID(), "Jane", "jane@example.com", passwordEncoder.encode("password2"));

            repository.save(user1);
            repository.save(user2);
        };
    }
}
