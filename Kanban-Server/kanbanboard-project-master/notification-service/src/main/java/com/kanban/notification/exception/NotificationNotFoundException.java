package com.kanban.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Task not found")
public class NotificationNotFoundException extends Exception{
}
