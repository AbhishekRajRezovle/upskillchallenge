package com.java.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.challenge.ChallengeApplication;
import com.java.challenge.dto.LoginRequestDTO;
import com.java.challenge.dto.RegistrationRequestDto;
import com.java.challenge.entity.User;
import com.java.challenge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestConfiguration.class, ChallengeApplication.class})
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserRepository userRepository;

    @Test
    void should_register_new_user() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequestDto registrationRequestDto = RegistrationRequestDto.builder()
                .email("testuser@gmail.com")
                .username("Username")
                .password("test@123")
                .build();

        User user = new User();
        user.setId(101l);
        user.setUsername(registrationRequestDto.username());
        user.setEmail(registrationRequestDto.email());
        user.setPassword(registrationRequestDto.password());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        MvcResult result = mockMvc.perform(
                        post("/api/authentication/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(registrationRequestDto)))
                .andExpect(status().isOk()).andReturn();


    }

    @Test
    void should_not_register_existing_user() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        RegistrationRequestDto registrationRequestDto = RegistrationRequestDto.builder()
                .email("testuser@gmail.com")
                .username("Username")
                .password("test@123")
                .build();
        User user = new User();
        user.setUsername(registrationRequestDto.username());
        user.setEmail(registrationRequestDto.email());
        user.setPassword(registrationRequestDto.password());
        when(userRepository.existsByEmail(registrationRequestDto.email())).thenReturn(true);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(
                        post("/api/authentication/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(registrationRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void should_login_existing_user() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        LoginRequestDTO loginDto = LoginRequestDTO.builder()
                .password("test@123")
                .email("testuser@gmail.com")
                .build();

        User existingUser = new User();
        existingUser.setId(1L);
        when(userRepository.findByEmail(loginDto.email())).thenReturn(existingUser);
        mockMvc.perform(
                        post("/api/authentication/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void
    should_receive_user_not_found_error_and_status_400() throws Exception {
        LoginRequestDTO loginDto = LoginRequestDTO.builder()
                .password("ANYTHING")
                .email("testuser@gmail.com")
                .build();

        mockMvc.perform(
                        post("/api/authentication/login")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}