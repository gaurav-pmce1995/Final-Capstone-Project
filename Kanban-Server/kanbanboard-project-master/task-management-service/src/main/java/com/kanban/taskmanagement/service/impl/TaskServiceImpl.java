package com.kanban.taskmanagement.service.impl;

import com.kanban.taskmanagement.exception.*;
import com.kanban.taskmanagement.model.Board;
import com.kanban.taskmanagement.model.Kanban;
import com.kanban.taskmanagement.model.Task;
import com.kanban.taskmanagement.model.TaskStatus;
import com.kanban.taskmanagement.repository.KanbanRepository;
import com.kanban.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final KanbanRepository kanbanRepository;

    @Override
    public Task createTask(String kanbanId, String boardName, String taskStatusName, Task newTask) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskAlreadyExistsException {

        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        TaskStatus taskStatus = getTaskStatusByName(board, taskStatusName);
        List<Task> tasks = taskStatus.getTasks();

        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(newTask.getTitle())) {
                throw new TaskAlreadyExistsException();
            }
        }

        newTask.setCreateDate(LocalDate.now());
        tasks.add(newTask);
        kanbanRepository.save(kanban);

        return newTask;
    }

    @Override
    public Task getTask(String kanbanId, String boardName, String taskStatusName, String taskName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        TaskStatus taskStatus = getTaskStatusByName(board, taskStatusName);
        List<Task> tasks = taskStatus.getTasks();

        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(taskName)) {
                return task;
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public List<Task> getAllTask(String kanbanId, String boardName, String taskStatusName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        TaskStatus taskStatus = getTaskStatusByName(board, taskStatusName);

        List<Task> tasks = taskStatus.getTasks();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return tasks;
    }

    @Override
    public Task updateTask(String kanbanId, String boardName, String taskStatusName, String taskName, Task updatedTask) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException {

        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        TaskStatus taskStatus = getTaskStatusByName(board, taskStatusName);
        List<Task> tasks = taskStatus.getTasks();

        for (Task task : tasks) {
            if (task.getTitle().equals(taskName)) {
                if (updatedTask.getTitle() != null) {
                    task.setTitle(updatedTask.getTitle());
                }
                if (updatedTask.getDescription() != null) {
                    task.setDescription(updatedTask.getDescription());
                }
                if (updatedTask.getSubTasks() != null) {
                    task.setSubTasks(updatedTask.getSubTasks());
                }
                if (updatedTask.getDueDate() != null) {
                    task.setDueDate(updatedTask.getDueDate());
                }
                if (updatedTask.getPriority() != null) {
                    task.setPriority(updatedTask.getPriority());
                }
                if (updatedTask.getAssignTo() != null) {
                    task.setAssignTo(updatedTask.getAssignTo());
                }
                if (updatedTask.getColor() != null) {
                    task.setColor(updatedTask.getColor());
                }

                kanbanRepository.save(kanban);
                return updatedTask;
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public void deleteTask(String kanbanId, String boardName, String taskStatusName, String taskName) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        TaskStatus taskStatus = getTaskStatusByName(board, taskStatusName);

        List<Task> tasks = taskStatus.getTasks();

        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(taskName)) {
                tasks.remove(task);
                kanbanRepository.save(kanban);
                return;
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public void assignTaskToUser(String kanbanId, String boardName, String taskStatusName, String taskName, String assignedUserEmail) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException {
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);
        TaskStatus taskStatus = getTaskStatusByName(board, taskStatusName);

        /*
        * TODO:
        *  Restrict the number of task to be assigned
        *  after first Task Status
         */

        List<Task> tasks = taskStatus.getTasks();

        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(taskName)) {
                task.getAssignTo().add(assignedUserEmail);

                kanbanRepository.save(kanban);
                return;
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public List<Task> getAssignedTask(String kanbanId, String boardName, String statusName, String email) {
        /*
         * TODO: pending implementation of Task Service
         *  How to implement not decided yet.
         *
         */
        return null;
    }


    @Override
    public void moveTaskToTaskStatus(String kanbanId, String boardName, String fromTaskStatus, String toTaskStatus, String taskTitle) throws KanbanNotFoundException, BoardNotFoundException, TaskStatusNotFoundException, TaskNotFoundException, TaskMoveException {
        String priority = "urgent";
        Kanban kanban = getKanbanById(kanbanId);
        Board board = getBoardByName(kanban, boardName);

        TaskStatus sourceTaskStatus = getTaskStatusByName(board, fromTaskStatus);
        TaskStatus destinationTaskStatus = getTaskStatusByName(board, toTaskStatus);

        Task taskToMove = fetchTask(sourceTaskStatus, taskTitle);

        if (priority.equalsIgnoreCase(taskToMove.getPriority())) {

            int sourceTaskStatusIndex = board.getTaskStatuses().indexOf(sourceTaskStatus);
            int destinationTaskStatusIndex = board.getTaskStatuses().indexOf(destinationTaskStatus);

            if (destinationTaskStatusIndex < sourceTaskStatusIndex) {
                throw new TaskMoveException();
            }
        }

        sourceTaskStatus.getTasks().remove(taskToMove);

        destinationTaskStatus.getTasks().add(taskToMove);

        kanbanRepository.save(kanban);
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

    private TaskStatus getTaskStatusByName(Board board, String taskStatusName) throws TaskStatusNotFoundException {
        List<TaskStatus> taskStatusList = board.getTaskStatuses();

        for (TaskStatus taskStatus : taskStatusList) {
            if (taskStatus.getTaskStatusName().equalsIgnoreCase(taskStatusName)) {
                return taskStatus;
            }
        }

        throw new TaskStatusNotFoundException();
    }

    private Task fetchTask(TaskStatus taskStatus, String taskTitle) throws TaskNotFoundException {
        List<Task> taskList = taskStatus.getTasks();

        for (Task task : taskList) {
            if (task.getTitle().equalsIgnoreCase(taskTitle)) {
                return task;
            }
        }
        throw new TaskNotFoundException();
    }

}
