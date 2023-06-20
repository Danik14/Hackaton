package diploma.project.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import diploma.project.data.User;
import diploma.project.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    // private final ModelMapper mapper;
    private final UserService service;

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok().body(service.getAllUsers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getOne(@PathVariable String uuid) {
        UUID id = UUID.fromString(uuid);
        if (uuid == null) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        return ResponseEntity.ok().body(service.getUserById(id));
    }

    // @PostMapping
    // public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto,
    // BindingResult bindingResult,
    // UriComponentsBuilder uriBuilder) {
    // if (bindingResult.hasErrors()) {
    // // Handle validation errors
    // Map<String, String> errors = new HashMap<>();
    // for (FieldError error : bindingResult.getFieldErrors()) {
    // errors.put(error.getField(), error.getDefaultMessage());
    // }
    // return ResponseEntity.badRequest().body(errors);
    // }

    // User user = mapper.map(userDto, User.class);
    // User createdUser = service.createUser(user);

    // // Build the URI for the created resource
    // UriComponents uriComponents =
    // uriBuilder.path("/users/{id}").buildAndExpand(createdUser.getId());
    // URI createdUri = uriComponents.toUri();

    // return ResponseEntity.created(createdUri).body(createdUser);
    // }

    public ResponseEntity<?> deleteUser(@PathVariable String uuid) {
        UUID id = UUID.fromString(uuid);
        if (uuid == null) {
            throw new IllegalArgumentException("Invalid UUID format");
        }

        service.deleteUser(id);

        return ResponseEntity.ok().body("User was successfuly deleted");
    }

}
