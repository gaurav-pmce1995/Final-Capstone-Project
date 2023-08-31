package com.kanban.userprofileservice.controller;

import com.kanban.userprofileservice.dto.UserProfileDTO;
import com.kanban.userprofileservice.exception.UserProfileAlreadyExistsException;
import com.kanban.userprofileservice.exception.UserProfileNotFoundException;
import com.kanban.userprofileservice.model.UserProfile;
import com.kanban.userprofileservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user-profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("")
    public ResponseEntity<?> addUserProfile(@RequestBody UserProfileDTO userProfileDTO) throws UserProfileAlreadyExistsException {
        try {
            UserProfile newUserProfile = userProfileService.addUserProfile(userProfileDTO);
            return new ResponseEntity<>(newUserProfile, HttpStatus.OK);
        } catch (UserProfileAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) throws UserProfileNotFoundException{
        try {
            String email=(String) request.getAttribute("current-user");
            UserProfile userProfile = userProfileService.getUserProfile(email);
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (UserProfileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserProfile(HttpServletRequest request, @RequestBody UserProfile userProfile) throws UserProfileNotFoundException {
        try {
            String email=(String) request.getAttribute("current-user");
            UserProfile updateUserProfile = userProfileService.updateUserProfile(email, userProfile);
            return new ResponseEntity<>(updateUserProfile, HttpStatus.OK);
        } catch (UserProfileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserProfile(HttpServletRequest request) throws UserProfileNotFoundException{
        try {
            String email=(String) request.getAttribute("current-user");
            userProfileService.deleteUserProfile(email);
            return new ResponseEntity<>("User's profile successfully deleted", HttpStatus.OK);
        } catch (UserProfileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, HttpServletRequest request) {
        try {
            String email = (String) request.getAttribute("current-user");
            String imageUrl = userProfileService.uploadImage(email, image);
            Map<String, String> response=new HashMap<>();
            response.put("imageURL",imageUrl);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error uploading image: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
