package com.kanban.authentication.proxy;

import com.kanban.authentication.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "kanban-service", url = "localhost:8080")
@FeignClient(name = "kanban-service", url = "kanbanservice:8080")
public interface UserProxy {
    @PostMapping("/api/v1/kanban-user")
    ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO);
}
