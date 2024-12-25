package com.github.ku4marez.clinicmanagement.service.impl;

import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.exception.UserNotFoundException;
import com.github.ku4marez.clinicmanagement.mapper.UserMapper;
import com.github.ku4marez.clinicmanagement.repository.UserRepository;
import com.github.ku4marez.clinicmanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.toEntity(userDTO);
        UserEntity savedUser = userRepository.save(userEntity);
        return modelMapper.toDto(savedUser);
    }

    @Override
    public UserDTO findUserByEmailCaseSensitive(String email) {
        UserEntity userEntity = userRepository.findByEmailCaseInsensitive(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return modelMapper.toDto(userEntity);
    }

    @Override
    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(modelMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        return usersPage.map(modelMapper::toDto);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        modelMapper.updateEntityFromDto(userDTO, existingUser);

        UserEntity updatedUser = userRepository.save(existingUser);
        return modelMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
