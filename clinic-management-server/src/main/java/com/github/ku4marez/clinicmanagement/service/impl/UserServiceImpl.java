package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.exception.UserNotFoundException;
import com.github.ku4marez.clinicmanagement.repository.UserRepository;
import com.github.ku4marez.clinicmanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = mapToEntity(userDTO);
        UserEntity savedUser = userRepository.save(userEntity);
        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO findUserByEmailCaseSensitive(String email) {
        UserEntity userEntity = userRepository.findByEmailCaseInsensitive(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return mapToDTO(userEntity);
    }

    @Override
    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        return usersPage.map(this::mapToDTO);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        Optional<UserEntity> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        UserEntity existingUser = existingUserOpt.get();
        existingUser.setFirstName(userDTO.firstName());
        existingUser.setLastName(userDTO.lastName());
        existingUser.setEmail(userDTO.email());
        existingUser.setPassword(userDTO.password());
        existingUser.setRole(userDTO.role());

        UserEntity updatedUser = userRepository.save(existingUser);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    private UserEntity mapToEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.firstName());
        userEntity.setLastName(userDTO.lastName());
        userEntity.setEmail(userDTO.email());
        userEntity.setPassword(userDTO.password());
        userEntity.setRole(userDTO.role());
        return userEntity;
    }

    private UserDTO mapToDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .build();
    }
}
