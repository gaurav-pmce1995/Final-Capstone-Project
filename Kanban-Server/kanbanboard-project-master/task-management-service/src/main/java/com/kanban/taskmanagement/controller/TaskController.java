package com.kanban.taskmanagement.controller;

import com.kanban.taskmanagement.exception.*;
import com.kanban.taskmanagement.model.Task;
import com.kanban.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kanbans/{kanbanId}/boards/{boardName}/task-statuses/{statusName}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public ResponseEntity<?> createTask(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            @RequestBody Task task)
            throws KanbanNotFoundException,
            TaskStatusNotFoundException,
            BoardNotFoundException,
            TaskAlreadyExistsException {
        try {
            Task newTask = taskService.createTask(kanbanId, boardName, statusName, task);
            return new ResponseEntity<>(newTask, HttpStatus.CREATED);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException |
                 TaskAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{taskName}")
    public ResponseEntity<?> getTaskByName(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            @PathVariable String taskName)
            throws KanbanNotFoundException,
            TaskNotFoundException,
            TaskStatusNotFoundException,
            BoardNotFoundException {
        try {
            Task fetchedTask = taskService.getTask(kanbanId, boardName, statusName, taskName);
            return new ResponseEntity<>(fetchedTask, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException |
                 TaskNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTask(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName)
            throws KanbanNotFoundException,
            TaskNotFoundException,
            TaskStatusNotFoundException,
            BoardNotFoundException {
        try {
            List<Task> tasks = taskService.getAllTask(kanbanId, boardName, statusName);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException |
                 TaskNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{taskName}")
    public ResponseEntity<?> updateTask(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            @PathVariable String taskName,
            @RequestBody Task task) throws KanbanNotFoundException, TaskStatusNotFoundException, BoardNotFoundException {
        try {
            Task updatedTask = taskService.updateTask(kanbanId, boardName, statusName, taskName, task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{taskName}/assign-task")
    public ResponseEntity<?> assignTaskToUser(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            @PathVariable String taskName,
            @RequestBody String assignedUserEmail,
            HttpServletRequest request)
            throws KanbanNotFoundException,
            TaskStatusNotFoundException,
            BoardNotFoundException,
            TaskNotFoundException {
        try {
            taskService.assignTaskToUser(kanbanId, boardName, statusName, taskName, assignedUserEmail);
            return new ResponseEntity<>("Task assigned to " + assignedUserEmail, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException |
                 TaskNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{taskName}")
    public ResponseEntity<?> deleteTask(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            @PathVariable String taskName)
            throws KanbanNotFoundException,
            TaskStatusNotFoundException,
            BoardNotFoundException,
            TaskNotFoundException {
        try {
            taskService.deleteTask(kanbanId, boardName, statusName, taskName);
            return new ResponseEntity<>("Task successfully deleted", HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException |
                 TaskNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/assigned-task")
    public ResponseEntity<?> getAssignedTask(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            HttpServletRequest request
            ) {
        String loggedInUserEmail = (String) request.getAttribute("current-user");
        List<Task> taskList = taskService.getAssignedTask(kanbanId, boardName, statusName, loggedInUserEmail);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @PostMapping("/move/{toTaskStatus}/{taskTitle}")
    public ResponseEntity<?> moveTask(
            @PathVariable String kanbanId,
            @PathVariable String boardName,
            @PathVariable String statusName,
            @PathVariable String toTaskStatus,
            @PathVariable String taskTitle)
            throws KanbanNotFoundException,
            TaskNotFoundException,
            TaskStatusNotFoundException,
            BoardNotFoundException, TaskMoveException {
        try {
            taskService.moveTaskToTaskStatus(kanbanId, boardName,statusName, toTaskStatus, taskTitle);
            return new ResponseEntity<>("Task moved successfully", HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException | TaskNotFoundException | TaskMoveException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
