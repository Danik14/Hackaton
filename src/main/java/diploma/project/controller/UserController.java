package diploma.project.controller;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import diploma.project.data.User;
import diploma.project.dto.UserDto;
import diploma.project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final ModelMapper mapper;
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok().body(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return service.createUser(user);
    }

}
