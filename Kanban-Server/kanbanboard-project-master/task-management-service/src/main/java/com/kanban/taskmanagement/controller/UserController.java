package com.kanban.taskmanagement.controller;

import com.kanban.taskmanagement.exception.UserNotFoundException;
import com.kanban.taskmanagement.model.User;
import com.kanban.taskmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping("/kanban-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/kanbans/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) throws UserNotFoundException {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Kanban User successfully deleted" ,HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/kanbans/users/get-user-details")
    public ResponseEntity<?> getUserDetails(HttpServletRequest request){
        String emailid = (String) request.getAttribute("current_user_emailid");
        return new ResponseEntity<>(userService.getUserDetails(emailid),HttpStatus.OK);
    }

}
