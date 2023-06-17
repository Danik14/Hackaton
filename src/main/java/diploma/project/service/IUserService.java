package diploma.project.service;

import java.util.List;
import java.util.UUID;

import diploma.project.data.User;

public interface IUserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserById(UUID id);
}
