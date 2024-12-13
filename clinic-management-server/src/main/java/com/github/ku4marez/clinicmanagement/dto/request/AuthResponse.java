package com.github.ku4marez.clinicmanagement.dto.request;

import lombok.Builder;

@Builder
public record AuthResponse(String token){}
