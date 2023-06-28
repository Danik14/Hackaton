package template.jwttemplate.service;

import java.util.List;
import java.util.UUID;

import template.jwttemplate.data.User;
import template.jwttemplate.dto.UserUpdateDto;

public interface UserService {
    // User createUser(User user);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    User getUserById(UUID id);

    void deleteUser(UUID id);

    int verifyUserEmail(String email);

    User updateUser(User user, UserUpdateDto userUpdateDto);
}
