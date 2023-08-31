package com.kanban.notification.service;

import com.kanban.notification.dto.KanbanDTO;
import com.kanban.notification.exception.NotificationNotFoundException;
import com.kanban.notification.model.Notification;
import com.kanban.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    /**
     * @param kanbanDTO
     */
    @RabbitListener(queues = "kanban.queue")
    @Override
    public void saveNotification(KanbanDTO kanbanDTO) {
        Notification notification = new Notification();
        String email = kanbanDTO.getJsonObject().get("email").toString();

        //if (notificationRepository.findByEmail(email).isEmpty()) {
        notification.setEmail(email);
        //}

        notification.setNotificationMessage("Kanban assign to you");
        notification.setKanbanName(kanbanDTO.getJsonObject());

        notificationRepository.save(notification);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public List<Notification> getAssignedKanbanNotification(String email) throws NotificationNotFoundException {
        //return notificationRepository.findByEmail(email);
        List<Notification> n = notificationRepository.findByEmail(email);
        if (n.isEmpty()) {
            throw new NotificationNotFoundException();
        }
        return n;
    }
}
