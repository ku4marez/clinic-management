package com.github.ku4marez.clinicmanagement.controller;

import com.github.ku4marez.clinicmanagement.configuration.JwtFilter;
import com.github.ku4marez.clinicmanagement.constant.TestConstants;
import com.github.ku4marez.clinicmanagement.constant.UserRole;
import com.github.ku4marez.clinicmanagement.dto.UserDTO;
import com.github.ku4marez.clinicmanagement.service.UserService;
import com.github.ku4marez.clinicmanagement.util.CreateEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.github.ku4marez.clinicmanagement.util.CreateEntityUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    @SuppressWarnings("unused")
    private JwtFilter jwtFilter;

    private UserDTO userDTO;

    @BeforeEach
    public void setup(){

        userDTO = CreateEntityUtil.createDefaultUserDTO();

    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users")
                        .with(SecurityMockMvcRequestPostProcessors.user(TestConstants.USERNAME).roles(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "firstName": "John",
                        "lastName": "Doe",
                        "email": "john.doe@example.com",
                        "password": "password",
                        "role": "PATIENT"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(CreateEntityUtil.DEFAULT_ID)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{id}", CreateEntityUtil.DEFAULT_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user(TestConstants.USERNAME).roles(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        when(userService.findUserByEmailCaseSensitive(CreateEntityUtil.OTHER_EMAIL)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/search")
                        .with(SecurityMockMvcRequestPostProcessors.user(TestConstants.USERNAME).roles(UserRole.ADMIN))
                        .param("email", CreateEntityUtil.OTHER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(OTHER_EMAIL))
                .andExpect(jsonPath("$.role").value(ADMIN_ROLE));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDTO> users = CreateEntityUtil.createUserDTOList();
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(new PageImpl<>(users));

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(TestConstants.USERNAME).roles(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.content[1].firstName").value(OTHER_FIRST_NAME));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(eq(CreateEntityUtil.DEFAULT_ID), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/{id}", CreateEntityUtil.DEFAULT_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user(TestConstants.USERNAME).roles(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "firstName": "Jane",
                        "lastName": "Smith",
                        "email": "jane.smith@example.com",
                        "password": "password2",
                        "role": "ADMIN"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(OTHER_LAST_NAME))
                .andExpect(jsonPath("$.email").value(OTHER_EMAIL));
    }

    @Test
    void testDeleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(CreateEntityUtil.DEFAULT_ID);

        mockMvc.perform(delete("/api/users/{id}", CreateEntityUtil.DEFAULT_ID)
                        .with(SecurityMockMvcRequestPostProcessors.user(TestConstants.USERNAME).roles(UserRole.ADMIN)))
                .andExpect(status().isOk());
    }
}