package com.java.challenge.dto;

import lombok.Builder;

public record DeleteRequestDTO(String email) {

    @Builder
    public DeleteRequestDTO {

    }
}
