package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.User.UserCreateDto;
import com.app.performtrackapi.dtos.User.UserResponseDto;
import com.app.performtrackapi.dtos.User.UserUpdateDto;
import com.app.performtrackapi.services.User.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<UserResponseDto>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(userService.getAllUser(pageable));
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDto> userCreate(@Valid @RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.createUser(userCreateDto), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserResponseDto> userUpdate(@PathVariable("id") UUID userId, @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> userDelete(@PathVariable("id") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
