package com.kanban.userprofileservice.service;

import com.kanban.userprofileservice.dto.UserProfileDTO;
import com.kanban.userprofileservice.exception.UserProfileAlreadyExistsException;
import com.kanban.userprofileservice.exception.UserProfileNotFoundException;
import com.kanban.userprofileservice.model.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserProfileService {
    UserProfile addUserProfile(UserProfileDTO newUserProfileDTO) throws UserProfileAlreadyExistsException;
    UserProfile getUserProfile(String email) throws UserProfileNotFoundException;
    UserProfile updateUserProfile(String email, UserProfile updatedUserProfile) throws UserProfileNotFoundException;
    void deleteUserProfile(String email) throws UserProfileNotFoundException;

    String uploadImage(String email, MultipartFile image) throws IOException, UserProfileNotFoundException;
}
