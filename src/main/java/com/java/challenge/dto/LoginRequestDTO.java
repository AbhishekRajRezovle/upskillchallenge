package com.java.challenge.dto;


import lombok.Builder;

public record LoginRequestDTO(String email, String password) {
    @Builder
    public LoginRequestDTO {
    }
}
