package com.java.challenge.dto;

import lombok.Builder;

public record UserResponseDto(Long id, String username, String email, String password, String status) {

    @Builder
    public UserResponseDto {

    }
}
