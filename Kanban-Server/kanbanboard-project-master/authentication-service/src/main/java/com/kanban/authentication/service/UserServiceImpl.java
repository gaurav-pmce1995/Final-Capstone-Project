package com.kanban.authentication.service;

import com.kanban.authentication.dto.LoginRequestDTO;
import com.kanban.authentication.dto.UserDTO;
import com.kanban.authentication.dto.UserRegistrationDTO;
import com.kanban.authentication.exception.InvalidCredentialException;
import com.kanban.authentication.exception.UserAlreadyExistsException;
import com.kanban.authentication.exception.UserNotFoundException;
import com.kanban.authentication.model.User;
import com.kanban.authentication.proxy.UserProfileProxy;
import com.kanban.authentication.proxy.UserProxy;
import com.kanban.authentication.repository.UserRepository;
import com.kanban.authentication.security.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtToken jwtToken;
    private final UserProxy userProxy;
    private final UserProfileProxy userProfileProxy;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtToken jwtToken, UserProxy userProxy, UserProfileProxy userProfileProxy, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtToken = jwtToken;
        this.userProxy = userProxy;
        this.userProfileProxy = userProfileProxy;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistsException {

        if (userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent() ||
                userRepository.findByUsername(userRegistrationDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        String email = userRegistrationDTO.getEmail();
        String username = userRegistrationDTO.getUsername();

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        UserDTO userDTO = new UserDTO(email, username);

        userProxy.saveUser(userDTO);
        userProfileProxy.saveUserProfile(userDTO);

    return userRepository.save(newUser);}


    @Override
    public User authenticateUser(LoginRequestDTO loginRequestDTO) throws UserNotFoundException, InvalidCredentialException {
        String usernameOrEmail = loginRequestDTO.getUsernameOrEmail();
        String password = loginRequestDTO.getPassword();

        Optional<User> userOptional = findByUsernameOrEmail(usernameOrEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else {
                throw new InvalidCredentialException();
            }
        }

        throw new UserNotFoundException();
    }

    @Override
    public User deleteUser(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return optionalUser.get();
        }
          throw new UserNotFoundException();


    }


    private Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }
}
