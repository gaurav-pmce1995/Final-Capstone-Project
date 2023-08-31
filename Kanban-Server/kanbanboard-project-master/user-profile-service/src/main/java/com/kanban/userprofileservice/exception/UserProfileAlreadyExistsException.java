package com.kanban.userprofileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User's profile already exists")
public class UserProfileAlreadyExistsException extends Exception{
}
