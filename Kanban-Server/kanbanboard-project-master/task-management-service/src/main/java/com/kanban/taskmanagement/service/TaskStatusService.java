package com.kanban.taskmanagement.service;

import com.kanban.taskmanagement.exception.BoardNotFoundException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.exception.TaskStatusAlreadyExistsException;
import com.kanban.taskmanagement.exception.TaskStatusNotFoundException;
import com.kanban.taskmanagement.model.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    TaskStatus createTaskStatus(String kanbanId, String boardName, String tasKStatusName) throws KanbanNotFoundException, TaskStatusAlreadyExistsException, BoardNotFoundException;

    TaskStatus getTaskStatus(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException;

    List<TaskStatus> getAllTaskStatus(String kanbanId, String boardName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException;

    TaskStatus updateTaskStatus(String kanbanId, String boardName, String taskStatusName, TaskStatus taskStatus) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException;

    boolean deleteTaskStatus(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException;
}

