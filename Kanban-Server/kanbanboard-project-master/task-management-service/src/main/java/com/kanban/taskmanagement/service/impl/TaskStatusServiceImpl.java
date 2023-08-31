package com.kanban.taskmanagement.service.impl;

import com.kanban.taskmanagement.exception.BoardNotFoundException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.exception.TaskStatusAlreadyExistsException;
import com.kanban.taskmanagement.exception.TaskStatusNotFoundException;
import com.kanban.taskmanagement.model.Board;
import com.kanban.taskmanagement.model.Kanban;
import com.kanban.taskmanagement.model.TaskStatus;
import com.kanban.taskmanagement.repository.KanbanRepository;
import com.kanban.taskmanagement.service.TaskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskStatusServiceImpl implements TaskStatusService {

    private final KanbanRepository kanbanRepository;

    @Override
    public TaskStatus createTaskStatus(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, TaskStatusAlreadyExistsException, BoardNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        List<TaskStatus> taskStatuses = board.getTaskStatuses();

        for (TaskStatus taskStatus : taskStatuses) {
            if (taskStatus.getTaskStatusName().equalsIgnoreCase(taskStatusName)) {
                throw new TaskStatusAlreadyExistsException();
            }
        }

        TaskStatus newTaskStatus = new TaskStatus();
        newTaskStatus.setTaskStatusName(taskStatusName);
        taskStatuses.add(newTaskStatus);
        kanbanRepository.save(kanban);

        return newTaskStatus;
    }

    @Override
    public TaskStatus getTaskStatus(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        List<TaskStatus> taskStatusList = board.getTaskStatuses();

        for (TaskStatus taskStatus : taskStatusList) {
            if (taskStatus.getTaskStatusName().equalsIgnoreCase(taskStatusName)) {
                return taskStatus;
            }
        }
        throw new TaskStatusNotFoundException();
    }

    @Override
    public List<TaskStatus> getAllTaskStatus(String kanbanId, String boardName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        List<TaskStatus> taskStatusList = board.getTaskStatuses();

        if (taskStatusList.isEmpty()) {
            throw new TaskStatusNotFoundException();
        }
        return taskStatusList;
    }

    @Override
    public TaskStatus updateTaskStatus(String kanbanId, String boardName, String taskStatusName, TaskStatus updatedTaskStatus) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        List<TaskStatus> taskStatusList = board.getTaskStatuses();

        for (TaskStatus taskStatus : taskStatusList) {
            if (taskStatus.getTaskStatusName().equals(taskStatusName)) {
                if (updatedTaskStatus.getTaskStatusName() != null) {
                    taskStatus.setTaskStatusName(updatedTaskStatus.getTaskStatusName());
                }
                taskStatus.setTaskLimitEnabled(updatedTaskStatus.isTaskLimitEnabled());
                kanbanRepository.save(kanban);
                return updatedTaskStatus;
            }
        }

        throw new TaskStatusNotFoundException();
    }

    @Override
    public boolean deleteTaskStatus(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        List<TaskStatus> taskStatusList = board.getTaskStatuses();

        for (TaskStatus taskStatus : taskStatusList) {
            if (taskStatus.getTaskStatusName().equalsIgnoreCase(taskStatusName)) {
                taskStatusList.remove(taskStatus);
                kanbanRepository.save(kanban);
                return true;
            }
        }
        throw new TaskStatusNotFoundException();
    }

    private Kanban getKanbanById(String kanbanId) throws KanbanNotFoundException {
        Optional<Kanban> optionalKanban = kanbanRepository.findById(kanbanId);
        if (optionalKanban.isPresent()) {
            return optionalKanban.get();
        }
        throw new KanbanNotFoundException();
    }

    private Board getBoardByName(Kanban kanban, String boardName) throws BoardNotFoundException {
        List<Board> boards = kanban.getBoards();
        for (Board board : boards) {
            if (board.getBoardName().equalsIgnoreCase(boardName)) {
                return board;
            }
        }
        throw new BoardNotFoundException();
    }
}
