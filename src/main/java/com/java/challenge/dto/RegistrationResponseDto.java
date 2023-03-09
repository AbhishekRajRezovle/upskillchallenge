package com.java.challenge.dto;

import lombok.Builder;

public record RegistrationResponseDto(String username, String email) {

    @Builder
    public RegistrationResponseDto {
    }
}
