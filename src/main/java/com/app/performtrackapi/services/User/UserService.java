package com.app.performtrackapi.services.User;

import com.app.performtrackapi.dtos.User.UserCreateDto;
import com.app.performtrackapi.dtos.User.UserResponseDto;
import com.app.performtrackapi.dtos.User.UserUpdateDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDto getUserByEmail(String email);
    List<UserResponseDto> getAllUser();
    UserResponseDto createUser(UserCreateDto userCreateDto);
    UserResponseDto updateUser(UUID id, UserUpdateDto userUpdateDto);
    void deleteUser(UUID id);
}
