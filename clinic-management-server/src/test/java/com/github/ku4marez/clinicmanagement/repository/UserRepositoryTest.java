package com.github.ku4marez.clinicmanagement.repository;

import com.github.ku4marez.clinicmanagement.entity.UserEntity;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.github.ku4marez.clinicmanagement.util.CreateEntityUtil.DEFAULT_EMAIL;
import static com.github.ku4marez.clinicmanagement.util.CreateEntityUtil.DEFAULT_FIRST_NAME;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindById_Success() {
        UserEntity user = CreateEntityUtil.createDefaultUserEntity();
        userRepository.save(user);

        Optional<UserEntity> result = userRepository.findById(CreateEntityUtil.DEFAULT_ID);

        assertTrue(result.isPresent());
        assertEquals(DEFAULT_FIRST_NAME, result.get().getFirstName());
    }

    @Test
    void testFindByEmailCaseInsensitive_Success() {
        UserEntity user = CreateEntityUtil.createDefaultUserEntity();
        userRepository.save(user);

        Optional<UserEntity> result = userRepository.findByEmailCaseInsensitive(DEFAULT_EMAIL);

        assertTrue(result.isPresent());
        assertEquals(DEFAULT_FIRST_NAME, result.get().getFirstName());
    }

    @Test
    void testFindByEmailCaseInsensitive_NotFound() {
        Optional<UserEntity> result = userRepository.findByEmailCaseInsensitive("nonexistent@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllUsers() {
        List<UserEntity> users = CreateEntityUtil.createUserEntityList();
        userRepository.saveAll(users);

        List<UserEntity> result = userRepository.findAll();

        assertEquals(2, result.size());
        assertEquals(DEFAULT_FIRST_NAME, result.get(0).getFirstName());
        assertEquals(DEFAULT_FIRST_NAME, result.get(1).getFirstName());
    }
}

