package com.java.challenge.dto;

import lombok.Builder;

public record LoginResponseDto(String username, String password, String email) {

    @Builder
    public LoginResponseDto {
    }
}
