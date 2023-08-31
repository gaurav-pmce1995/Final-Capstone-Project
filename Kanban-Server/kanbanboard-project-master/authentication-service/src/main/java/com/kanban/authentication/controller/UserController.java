package com.kanban.authentication.controller;

import com.kanban.authentication.dto.LoginRequestDTO;
import com.kanban.authentication.dto.UserRegistrationDTO;
import com.kanban.authentication.exception.InvalidCredentialException;
import com.kanban.authentication.exception.UserAlreadyExistsException;
import com.kanban.authentication.exception.UserNotFoundException;
import com.kanban.authentication.model.User;
import com.kanban.authentication.security.JwtToken;
import com.kanban.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtToken jwtToken;

    @Autowired
    public UserController(UserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @PostMapping("/signup")
    ResponseEntity<?> register(@RequestBody UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException {
        try {
           User user= userService.createUser(userRegistrationDTO);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            throw new UserAlreadyExistsException();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws UserNotFoundException, InvalidCredentialException {
        try {
            User user = userService.authenticateUser(loginRequestDTO);
            Map<String, String> token = jwtToken.generateToken(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (UserNotFoundException | InvalidCredentialException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) throws UserNotFoundException {
        try {

            return new ResponseEntity<>( userService.deleteUser(email), HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
