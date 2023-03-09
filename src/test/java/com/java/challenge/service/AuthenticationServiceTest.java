package com.java.challenge.service;

import com.java.challenge.dto.LoginRequestDTO;
import com.java.challenge.dto.RegistrationRequestDto;
import com.java.challenge.dto.ResponseDto;
import com.java.challenge.entity.User;
import com.java.challenge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Test
    void should_register_new_user(){
        User registerUser= testUser();
        when((userRepository.findByEmail(Mockito.any()))).thenReturn(registerUser);
        User user= authenticationService.fetchUserByEmail(registerUser.getEmail());
        assertThat(user).isEqualTo(user);
    }

    @Test
    void should_login_existing_user(){
        User registerUser= testUser();
        when((userRepository.findByEmail(Mockito.any()))).thenReturn(registerUser);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        ResponseDto response=  authenticationService.userLogin(testLoginDTO());
        assertThat(200).isEqualTo(response.getCode());

    }

    private static LoginRequestDTO testLoginDTO() {
        return LoginRequestDTO.builder().password("test@123").email("testuser@gmail.com").build();
    }

    private static RegistrationRequestDto testRegistrationDto() {


        return RegistrationRequestDto.builder()
                .email("testuser@gmail.com")
                .username("testUser")
                .password("test@123")
                .build();

    }


    private static User testUser() {
        User user = new User();
        user.setId(1l);
        user.setUsername("testusername@test.com");
        user.setPassword("Test@123");
        user.setEmail("testusername@test.com");
        return user;
    }
}
