package com.kanban.taskmanagement.service.impl;

import com.kanban.taskmanagement.exception.UserNotFoundException;
import com.kanban.taskmanagement.model.User;
import com.kanban.taskmanagement.repository.UserRepository;
import com.kanban.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.insert(user);
    }

    @Override
    public void deleteUser(String id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        }
        throw new UserNotFoundException();
    }
    @Override
    public User getUserDetails(String emailId) {
        return userRepository.findById(emailId).get();
    }

}
