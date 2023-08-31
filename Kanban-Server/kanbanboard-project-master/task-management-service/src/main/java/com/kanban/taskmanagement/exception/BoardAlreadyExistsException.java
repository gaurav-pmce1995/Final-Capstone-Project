package com.kanban.taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Board already exists")
public class BoardAlreadyExistsException extends Exception{
}
