package ru.aston.purchases.service;

import ru.aston.purchases.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto findById(Long id);

    List<UserDto> getAllUsers();

    UserDto saveUser(UserDto userDTO);

    void delete(Long id);

    UserDto updateUser(Long id, UserDto userDTO);
}