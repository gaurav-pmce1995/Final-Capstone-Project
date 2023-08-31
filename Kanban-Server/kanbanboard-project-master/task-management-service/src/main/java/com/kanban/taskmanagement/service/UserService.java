package com.kanban.taskmanagement.service;

import com.kanban.taskmanagement.exception.UserNotFoundException;
import com.kanban.taskmanagement.model.User;

public interface UserService {
    void addUser(User user);
    void deleteUser(String id) throws UserNotFoundException;
    public abstract User getUserDetails(String emailId);
}
