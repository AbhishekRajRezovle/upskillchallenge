package com.java.challenge.controller;

import com.java.challenge.dto.LoginRequestDTO;
import com.java.challenge.dto.RegistrationRequestDto;
import com.java.challenge.dto.ResponseDto;
import com.java.challenge.service.AuthenticationService;
import com.java.challenge.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String tokenHeader;
    @Value("${jwt.prefix}")
    private String tokenPrefix;

    @PostMapping("/register")
    public ResponseDto registerUser(@Valid @RequestBody RegistrationRequestDto request) {

        return authenticationService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDTO loginDto, HttpServletResponse response) {

        response.addHeader(tokenHeader, tokenPrefix + jwtTokenUtil.generateToken(loginDto.email()));
        return authenticationService.userLogin(loginDto);
    }
}
