package diploma.project.service;

import org.springframework.stereotype.Service;

import diploma.project.data.User;
import diploma.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User createUser(User user) {
        if (repository.existsByUserName(user.getUsername())) {

        }
    }
}
