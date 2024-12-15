package com.github.ku4marez.clinicmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ku4marez.clinicmanagement.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDTO(
        @JsonProperty("firstName") @NotBlank(message = "First name is required") String firstName,
        @JsonProperty("lastName") @NotBlank(message = "Last name is required") String lastName,
        @JsonProperty("email") @Email(message = "Invalid email format") String email,
        @JsonProperty("password") @NotBlank(message = "Password is required") String password,
        @JsonProperty("role") Role role
) {}
