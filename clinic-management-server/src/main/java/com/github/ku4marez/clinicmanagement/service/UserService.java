package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO findUserByEmailCaseSensitive(String email);

    UserDTO getUserById(String id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(String id, UserDTO userDTO);

    void deleteUser(String id);
}
