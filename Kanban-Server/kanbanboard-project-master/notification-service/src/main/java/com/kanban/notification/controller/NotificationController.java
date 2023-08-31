package com.kanban.notification.controller;

import com.kanban.notification.exception.NotificationNotFoundException;
import com.kanban.notification.model.Notification;
import com.kanban.notification.service.NotificationService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("")
    public ResponseEntity<?> getAllNotification(HttpServletRequest request) throws NotificationNotFoundException {
//        Claims claims = (Claims) request.getAttribute("claims");
//        System.out.println("Deepak Notification test: " + claims);
//        String email = claims.getSubject();
//        System.out.println("Deepak Notification test: " + email);
//        return new ResponseEntity<>(notificationService.getAssignedKanbanNotification(email), HttpStatus.OK);
//
        Claims claims = (Claims) request.getAttribute("claims");
        String email = claims.getSubject();
        System.out.println("Deepak Notification test: " + email);
        try {
            List<Notification> ns = notificationService.getAssignedKanbanNotification(email);
            return new ResponseEntity<>(ns, HttpStatus.OK);
        } catch (NotificationNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
