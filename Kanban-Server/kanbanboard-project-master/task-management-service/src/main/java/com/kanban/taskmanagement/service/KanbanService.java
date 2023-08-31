package com.kanban.taskmanagement.service;

import com.kanban.taskmanagement.exception.KanbanAlreadyExistsException;
import com.kanban.taskmanagement.exception.KanbanNotFoundException;
import com.kanban.taskmanagement.model.Kanban;

import java.util.List;

public interface KanbanService {
    Kanban createKanban(Kanban kanban, String email) throws KanbanAlreadyExistsException;
    Kanban getKanbanById(String kanbanId) throws KanbanNotFoundException;
    List<Kanban> getKanbanByEmail(String email) throws KanbanNotFoundException;
    void deleteKanban(String kanbanId) throws KanbanNotFoundException;
    Kanban updateKanban(String kanbanId, Kanban kanban) throws KanbanNotFoundException;
    Kanban assignKanbanToUser(String kanbanId, String assigneeEmail) throws KanbanNotFoundException;

    List<Kanban> getAssignedKanban(String email) throws KanbanNotFoundException;
}
