package com.kanban.userprofileservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authentication-service", url = "authenticationservice:5000")
public interface UserProxy {

    @DeleteMapping("/api/v1/users/{email}")
    ResponseEntity<?> deleteUserFromAuthenticationService(@PathVariable String email);
}
