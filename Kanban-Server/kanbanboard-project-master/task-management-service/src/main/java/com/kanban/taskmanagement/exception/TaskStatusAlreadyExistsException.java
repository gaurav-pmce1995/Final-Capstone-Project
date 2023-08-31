package com.kanban.taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Task Status already exists")
public class TaskStatusAlreadyExistsException extends Exception{
}
