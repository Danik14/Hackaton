package template.jwttemplate;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import template.jwttemplate.data.User;
import template.jwttemplate.enums.UserRole;
import template.jwttemplate.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class ProjectApplication {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
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

}
