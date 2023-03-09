package com.java.challenge.service;

import com.java.challenge.dto.LoginRequestDTO;
import com.java.challenge.dto.RegistrationRequestDto;
import com.java.challenge.dto.ResponseDto;
import com.java.challenge.entity.User;

public interface AuthenticationService {

    ResponseDto registerUser(RegistrationRequestDto registrationDto);

    ResponseDto userLogin(LoginRequestDTO loginDTO);

    User fetchUserByEmail(String email);
}
