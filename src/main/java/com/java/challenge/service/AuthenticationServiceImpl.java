package com.java.challenge.service;

import com.java.challenge.aop.TrackExecutionTime;
import com.java.challenge.dto.*;
import com.java.challenge.entity.User;
import com.java.challenge.entity.UserStatus;
import com.java.challenge.exception.ServiceException;
import com.java.challenge.repository.UserRepository;
import com.java.challenge.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @TrackExecutionTime
    public ResponseDto registerUser(RegistrationRequestDto registrationDto) {

        if (userRepository.existsByEmail(registrationDto.email())) {
            throw new ServiceException(ApplicationConstants.ERROR_STATUS_CODE, HttpStatus.OK, ApplicationConstants.USER_ALREADY_EXIST);
        }

        User user = userRepository.save(User.builder()
                .email(registrationDto.email())
                .status(UserStatus.ACTIVE)
                .username(registrationDto.username())
                .createdAt(LocalDateTime.now())
                .password(passwordEncoder.encode(registrationDto.password()))
                .build());

        return new SuccessResponseDto(RegistrationResponseDto
                .builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build());
    }

    @Override
    @TrackExecutionTime
    public ResponseDto userLogin(LoginRequestDTO loginDTO) {
        User existingUser = userRepository.findByEmail(loginDTO.email());
        if (existingUser == null) {
            return new ErrorResponseDto(ApplicationConstants.HTTP_RESPONSE_ERROR_CODE,
                    ApplicationConstants.HTTP_RESPONSE_ERROR_CODE_NOT_FOUND_MSG);
        } else if (!passwordEncoder.matches(loginDTO.password(), existingUser.getPassword())) {
            return new ErrorResponseDto(ApplicationConstants.HTTP_RESPONSE_ERROR_CODE,
                    ApplicationConstants.PASSWORD_MISMATCH);
        } else {
            return new SuccessResponseDto(LoginResponseDto.builder()
                    .email(existingUser.getEmail())
                    .password(existingUser.getPassword())
                    .username(existingUser.getUsername())
                    .build());
        }
    }

    @Override
    public User fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
