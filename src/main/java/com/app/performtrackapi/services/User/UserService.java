package com.app.performtrackapi.services.User;

import com.app.performtrackapi.dtos.User.UserCreateDto;
import com.app.performtrackapi.dtos.User.UserResponseDto;
import com.app.performtrackapi.dtos.User.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserResponseDto getUserByEmail(String email);
    Page<UserResponseDto> getAllUser(Pageable pageable);
    UserResponseDto createUser(UserCreateDto userCreateDto);
    UserResponseDto updateUser(UUID id, UserUpdateDto userUpdateDto);
    void deleteUser(UUID id);
}
