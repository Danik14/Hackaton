package diploma.project.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult,
            UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        User user = mapper.map(userDto, User.class);
        User createdUser = service.createUser(user);

        // Build the URI for the created resource
        UriComponents uriComponents = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.getId());
        URI createdUri = uriComponents.toUri();

        return ResponseEntity.created(createdUri).body(createdUser);
    }

}
