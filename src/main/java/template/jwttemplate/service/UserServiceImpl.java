package template.jwttemplate.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import template.jwttemplate.data.User;
import template.jwttemplate.exception.UserNotFoundException;
import template.jwttemplate.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    // private final PasswordEncoder passwordEncoder;

    // public User createUser(User user) {
    // if (repository.existsByEmail(user.getEmail())) {
    // throw new UserAlreadyExistsException("User with such email already exists");
    // } else if (repository.existsByUsername(user.getUsername())) {
    // throw new UserAlreadyExistsException("User with such username already
    // exists");
    // }

    // user.setPassword(passwordEncoder.encode(user.getPassword()));

    // return repository.save(user);
    // }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with such email not found"));
    }

    @Override
    public User getUserById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("User with such id not found"));
    }

    @Override
    public void deleteUser(UUID id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException("User with such id not found");
        }
        repository.deleteById(id);
    }
}
