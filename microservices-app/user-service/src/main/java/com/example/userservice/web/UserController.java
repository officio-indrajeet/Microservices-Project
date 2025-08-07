package com.example.userservice.web;

import com.example.common.api.ApiResponse;
import com.example.userservice.domain.User;
import com.example.userservice.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST adapter for user endpoints.
 * Pattern: Controller-Adapter.
 */
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> list() {
        return ResponseEntity.ok(ApiResponse.ok(userService.listUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.getUser(id)));
    }

    record CreateUserRequest(@NotBlank String name, @Email String email) {}

    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@RequestBody CreateUserRequest request) {
        User created = userService.createUser(request.name(), request.email());
        return ResponseEntity.ok(ApiResponse.ok(created));
    }
}