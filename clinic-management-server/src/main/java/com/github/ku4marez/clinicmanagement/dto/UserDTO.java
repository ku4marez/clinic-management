package com.github.ku4marez.clinicmanagement.dto;

import com.github.ku4marez.clinicmanagement.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDTO(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @Email(message = "Invalid email format") String email,
        @NotBlank(message = "Password is required") String password,
        Role role
) {}
