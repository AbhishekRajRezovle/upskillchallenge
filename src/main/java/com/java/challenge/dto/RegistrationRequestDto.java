package com.java.challenge.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;


public record RegistrationRequestDto(@NotNull(message = "Username can't be null") String username,
                                     @NotNull(message = "Email can't be null") String email,
                                     @NotNull(message = "Password can't be null") String password) {
    @Builder
    public RegistrationRequestDto {
    }
}
