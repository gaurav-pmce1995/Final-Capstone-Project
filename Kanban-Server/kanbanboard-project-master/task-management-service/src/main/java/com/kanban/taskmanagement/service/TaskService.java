package com.kanban.taskmanagement.service;

import com.kanban.taskmanagement.exception.*;
import com.kanban.taskmanagement.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(String kanbanId, String boardName, String taskStatusName, Task newTask) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskAlreadyExistsException;

    Task getTask(String kanbanId, String boardName, String taskStatusName, String taskName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException;

    List<Task> getAllTask(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException;

    Task updateTask(String kanbanId, String boardName, String statusName, String taskName, Task task) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException;

    void deleteTask(String kanbanId, String boardName, String statusName, String taskName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException;

    void assignTaskToUser(String kanbanId, String boardName, String statusName, String taskName, String assignedUserEmail) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException;
    List<Task> getAssignedTask(String kanbanId, String boardName, String statusName, String email);
    void moveTaskToTaskStatus(String kanbanId, String boardName, String fromTaskStatus, String toTaskStatus, String taskTitle) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException, TaskMoveException;
}
