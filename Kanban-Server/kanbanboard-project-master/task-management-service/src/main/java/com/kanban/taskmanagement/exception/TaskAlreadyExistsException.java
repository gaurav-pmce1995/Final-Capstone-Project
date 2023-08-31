package com.kanban.taskmanagement.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Task Already Exists")
public class TaskAlreadyExistsException extends Exception{
}
