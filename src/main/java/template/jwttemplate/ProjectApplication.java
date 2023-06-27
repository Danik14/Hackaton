package template.jwttemplate;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import template.jwttemplate.data.User;
import template.jwttemplate.enums.Role;
import template.jwttemplate.repository.UserRepository;
import template.jwttemplate.service.EmailService;

@SpringBootApplication
@RequiredArgsConstructor
@PropertySource(value = ".env", encoding = "UTF-8")
public class ProjectApplication {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final EmailService emailService;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner generateUsers() {
		return args -> {
			User user1 = new User(UUID.randomUUID(), Role.USER, "John", "john@example.com",
					passwordEncoder.encode("password1"), true, true);
			User user2 = new User(UUID.randomUUID(), Role.USER, "Jane", "jane@example.com",
					passwordEncoder.encode("password2"), true, true);
			User user3 = new User(UUID.randomUUID(), Role.ADMIN, "Ryan", "gosling@example.com",
					passwordEncoder.encode("password3"), true, true);
			User user4 = new User(UUID.randomUUID(), Role.USER, "Papzan", "papzan@example.com",
					passwordEncoder.encode("password4"), true, true);
			User user5 = new User(UUID.randomUUID(), Role.ADMIN, "Danik", "danik@example.com",
					passwordEncoder.encode("danik12345"), true, true);

			repository.save(user1);
			repository.save(user2);
			repository.save(user3);
			repository.save(user4);
			repository.save(user5);

			// emailService.sendConfirmEmail("mrvirtus3@gmail.com");
		};
	}

}
