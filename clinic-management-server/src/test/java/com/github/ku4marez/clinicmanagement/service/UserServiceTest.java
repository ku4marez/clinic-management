package com.github.ku4marez.clinicmanagement.service;

import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.entity.enums.Role;
import com.github.ku4marez.clinicmanagement.exception.UserNotFoundException;
import com.github.ku4marez.clinicmanagement.repository.UserRepository;
import com.github.ku4marez.clinicmanagement.service.impl.UserServiceImpl;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.github.ku4marez.clinicmanagement.util.CreateEntityUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUserById() {
        UserEntity user = CreateEntityUtil.createDefaultUserEntity();
        when(userRepository.findById(CreateEntityUtil.DEFAULT_ID)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(CreateEntityUtil.DEFAULT_ID);

        assertEquals(CreateEntityUtil.DEFAULT_FIRST_NAME, result.firstName());
        verify(userRepository, times(1)).findById(CreateEntityUtil.DEFAULT_ID);
    }

    @Test
    void testUserNotFoundException() {
        when(userRepository.findById(CreateEntityUtil.DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(CreateEntityUtil.DEFAULT_ID));
    }

    @Test
    void testCreateUser() {
        UserDTO userDTO = CreateEntityUtil.createDefaultUserDTO();
        UserEntity userEntity = CreateEntityUtil.createDefaultUserEntity();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(CreateEntityUtil.DEFAULT_FIRST_NAME, result.firstName());
        assertEquals(CreateEntityUtil.DEFAULT_LAST_NAME, result.lastName());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(CreateEntityUtil.DEFAULT_ID);

        verify(userRepository, times(1)).deleteById(CreateEntityUtil.DEFAULT_ID);
    }

    @Test
    void testGetAllUsers() {
        Pageable pageable = PageRequest.of(0, 2);
        List<UserEntity> users = CreateEntityUtil.createUserEntityList();
        Page<UserEntity> userPage = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserDTO> result = userService.getAllUsers(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(DEFAULT_FIRST_NAME, result.getContent().get(0).firstName());
        assertEquals(OTHER_FIRST_NAME, result.getContent().get(1).firstName());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void testUpdateUser_Success() {
        UserEntity existingUser = CreateEntityUtil.createDefaultUserEntity();
        UserDTO userDTO = CreateEntityUtil.createOtherUserDTO();

        when(userRepository.findById(CreateEntityUtil.DEFAULT_ID)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser);

        UserDTO updatedUser = userService.updateUser(CreateEntityUtil.DEFAULT_ID, userDTO);

        assertEquals(OTHER_FIRST_NAME, updatedUser.firstName());
        verify(userRepository, times(1)).findById(CreateEntityUtil.DEFAULT_ID);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testFindUserByEmailCaseSensitive_Success() {
        UserEntity user = CreateEntityUtil.createUserEntity(DEFAULT_ID, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL, DEFAULT_PASSWORD, Role.PATIENT);

        when(userRepository.findByEmailCaseInsensitive(DEFAULT_EMAIL)).thenReturn(Optional.of(user));

        UserDTO result = userService.findUserByEmailCaseSensitive(DEFAULT_EMAIL);

        assertEquals(DEFAULT_FIRST_NAME, result.firstName());
        verify(userRepository, times(1)).findByEmailCaseInsensitive(DEFAULT_EMAIL);
    }

    @Test
    void testFindUserByEmailCaseSensitive_UserNotFound() {
        when(userRepository.findByEmailCaseInsensitive(DEFAULT_EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmailCaseSensitive(DEFAULT_EMAIL));
    }
}


