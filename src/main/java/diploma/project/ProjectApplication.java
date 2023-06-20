package diploma.project;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import diploma.project.data.User;
import diploma.project.enums.UserRole;
import diploma.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

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
