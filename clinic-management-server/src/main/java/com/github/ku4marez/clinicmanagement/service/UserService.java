package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO findUserByEmailCaseSensitive(String email);

    UserDTO getUserById(String id);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO updateUser(String id, UserDTO userDTO);

    void deleteUser(String id);
}
