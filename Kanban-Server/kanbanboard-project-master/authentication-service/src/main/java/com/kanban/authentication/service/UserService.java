package com.kanban.authentication.service;

import com.kanban.authentication.dto.LoginRequestDTO;
import com.kanban.authentication.dto.UserRegistrationDTO;
import com.kanban.authentication.exception.InvalidCredentialException;
import com.kanban.authentication.exception.UserAlreadyExistsException;
import com.kanban.authentication.exception.UserNotFoundException;
import com.kanban.authentication.model.User;

public interface UserService {

    User createUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException;

    User authenticateUser(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, InvalidCredentialException;
    User deleteUser(String email) throws UserNotFoundException;
}
