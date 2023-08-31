package com.kanban.taskmanagement.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Task not found")
public class TaskNotFoundException extends Exception{
}
