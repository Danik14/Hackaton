package diploma.project.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import diploma.project.data.User;
import diploma.project.exception.UserAlreadyExistsException;
import diploma.project.exception.UserNotFoundException;
import diploma.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("User with such email already exists");
        } else if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with such username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User getUserById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }
}
