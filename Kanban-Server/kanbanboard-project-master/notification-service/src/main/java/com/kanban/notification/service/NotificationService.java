package com.kanban.notification.service;

import com.kanban.notification.dto.KanbanDTO;
import com.kanban.notification.exception.NotificationNotFoundException;
import com.kanban.notification.model.Notification;

import java.util.List;

public interface NotificationService {
    void saveNotification(KanbanDTO kanbanDTO);
    List<Notification> getAssignedKanbanNotification(String email) throws NotificationNotFoundException;
}
