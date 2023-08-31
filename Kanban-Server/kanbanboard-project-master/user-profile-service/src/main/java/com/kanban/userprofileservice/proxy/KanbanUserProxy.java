package com.kanban.userprofileservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "task-management-service", url = "kanbanservice:8080")
public interface KanbanUserProxy {

    @DeleteMapping("/api/v1/kanbans/users/{id}")
    ResponseEntity<?> deleteUserFromTaskManagementService(@PathVariable String id);
}
