package com.java.challenge.service;

import com.java.challenge.dto.*;
import com.java.challenge.entity.User;
import com.java.challenge.entity.UserStatus;
import com.java.challenge.exception.ServiceException;
import com.java.challenge.repository.UserRepository;
import com.java.challenge.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseDto fetchAllUser() {
        return new SuccessResponseDto(userRepository.findAll().stream().map(user -> UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.getStatus().toString())
                .password(user.getPassword())
                .username(user.getUsername())
                .build()).collect(Collectors.toList()));
    }

    @Override
    public ResponseDto deleteUserByEmail(DeleteRequestDTO deleteRequestDTO) {
        User user = userRepository.findByEmail(deleteRequestDTO.email());
        if (user == null) {
            throw new ServiceException(ApplicationConstants.ERROR_STATUS_CODE, HttpStatus.OK, ApplicationConstants.USER_ALREADY_EXIST);
        }

        user.setStatus(UserStatus.DEACTIVATE);
        userRepository.save(user);
        return new SuccessResponseDto(user);
    }
}
