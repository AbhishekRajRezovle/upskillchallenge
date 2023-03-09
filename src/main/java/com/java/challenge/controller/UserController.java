package com.java.challenge.controller;

import com.java.challenge.dto.DeleteRequestDTO;
import com.java.challenge.dto.ResponseDto;
import com.java.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseDto fetchAllUsr() {

        return userService.fetchAllUser();
    }

    @DeleteMapping()
    public ResponseDto deleteUserByEmail(@RequestBody DeleteRequestDTO deleteRequestDTO) {

        return userService.deleteUserByEmail(deleteRequestDTO);
    }

}
