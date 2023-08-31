package com.kanban.taskmanagement.controller;

import com.kanban.taskmanagement.exception.BoardNotFoundException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.exception.TaskStatusAlreadyExistsException;
import com.kanban.taskmanagement.exception.TaskStatusNotFoundException;
import com.kanban.taskmanagement.model.TaskStatus;
import com.kanban.taskmanagement.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kanbans/{kanbanId}/boards/{boardName}/task-statuses")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskStatusService statusService;

    @PostMapping("")
    public ResponseEntity<?> createTaskStatus(@PathVariable String kanbanId, @PathVariable String boardName, @RequestBody TaskStatus taskStatus) throws KanbanNotFoundException, TaskStatusAlreadyExistsException, BoardNotFoundException {
        try {
            TaskStatus newTaskStatus = statusService.createTaskStatus(kanbanId, boardName, taskStatus.getTaskStatusName());
            return new ResponseEntity<>(newTaskStatus, HttpStatus.CREATED);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{taskStatusName}")
    public ResponseEntity<?> getTaskStatusByName(@PathVariable String kanbanId, @PathVariable String boardName, @PathVariable String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException {
        try {
            TaskStatus taskStatus = statusService.getTaskStatus(kanbanId, boardName, taskStatusName);
            return new ResponseEntity<>(taskStatus, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTaskStatus(@PathVariable String kanbanId, @PathVariable String boardName) throws KanbanNotFoundException, TaskStatusNotFoundException, BoardNotFoundException {
        try {
            List<TaskStatus> taskStatusList = statusService.getAllTaskStatus(kanbanId, boardName);
            return new ResponseEntity<>(taskStatusList, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{taskStatusName}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable String kanbanId, @PathVariable String boardName, @PathVariable String taskStatusName, @RequestBody TaskStatus taskStatus) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException {
        try {
            TaskStatus updatedTaskStatus = statusService.updateTaskStatus(kanbanId, boardName, taskStatusName, taskStatus);
            return new ResponseEntity<>(updatedTaskStatus, HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{taskStatusName}")
    public ResponseEntity<?> deleteTaskStatus(@PathVariable String kanbanId, @PathVariable String boardName, @PathVariable String taskStatusName) throws KanbanNotFoundException, TaskStatusNotFoundException, BoardNotFoundException {
        try {
            statusService.deleteTaskStatus(kanbanId, boardName, taskStatusName);
            return new ResponseEntity<>("Task Status successfully deleted", HttpStatus.OK);
        } catch (KanbanNotFoundException | BoardNotFoundException | TaskStatusNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
