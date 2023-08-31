package com.kanban.authentication.proxy;

import com.kanban.authentication.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service", url = "userprofileservice:9292")
public interface UserProfileProxy {

    @PostMapping("/api/v1/user-profile")
    ResponseEntity<?> saveUserProfile(@RequestBody UserDTO userDTO);
}
