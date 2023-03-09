package com.java.challenge.service;

import com.java.challenge.dto.DeleteRequestDTO;
import com.java.challenge.dto.ResponseDto;

public interface UserService {

    ResponseDto fetchAllUser();

    ResponseDto deleteUserByEmail(DeleteRequestDTO deleteRequestDTO);
}
