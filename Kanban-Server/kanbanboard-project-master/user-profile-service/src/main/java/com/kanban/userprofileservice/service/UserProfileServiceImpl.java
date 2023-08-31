package com.kanban.userprofileservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.kanban.userprofileservice.dto.UserProfileDTO;
import com.kanban.userprofileservice.exception.UserProfileAlreadyExistsException;
import com.kanban.userprofileservice.exception.UserProfileNotFoundException;
import com.kanban.userprofileservice.model.UserProfile;
import com.kanban.userprofileservice.proxy.KanbanUserProxy;
import com.kanban.userprofileservice.proxy.UserProxy;
import com.kanban.userprofileservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserProxy userProxy;
    private final KanbanUserProxy kanbanUserProxy;


    @Override
    public UserProfile addUserProfile(UserProfileDTO userProfileDTO) throws UserProfileAlreadyExistsException {
        UserProfile userProfile = userProfileRepository.findByEmail(userProfileDTO.getEmail());
        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setEmail(userProfileDTO.getEmail());

            return userProfileRepository.insert(userProfile);
        }

        throw new UserProfileAlreadyExistsException();
    }

    @Override
    public UserProfile getUserProfile(String email) throws UserProfileNotFoundException {
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if (userProfile!=null) {
            return userProfile;
        }
        throw new UserProfileNotFoundException();
    }

    @Override
    public UserProfile updateUserProfile(String email, UserProfile updatedUserProfile) throws UserProfileNotFoundException {
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if (userProfile!=null) {

            if (updatedUserProfile.getFirstName() != null) {
                userProfile.setFirstName(updatedUserProfile.getFirstName());
            }

            if (updatedUserProfile.getLastName() != null) {
                userProfile.setLastName(updatedUserProfile.getLastName());
            }

            if (updatedUserProfile.getEmail() != null) {
                userProfile.setEmail(updatedUserProfile.getEmail());
            }

            if (updatedUserProfile.getMobile() != 0) {
                userProfile.setMobile(updatedUserProfile.getMobile());
            }

            if (updatedUserProfile.getProfilePictureUrl() != null) {
                userProfile.setProfilePictureUrl(updatedUserProfile.getProfilePictureUrl());
            }

            userProfileRepository.save(userProfile);
            return userProfile;
        }
        throw new UserProfileNotFoundException();
    }

    @Override
    public void deleteUserProfile(String email) throws UserProfileNotFoundException {
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if (userProfile!=null) {
            // Also delete User data
            // from Authentication Service
            userProxy.deleteUserFromAuthenticationService(email);
            kanbanUserProxy.deleteUserFromTaskManagementService(email);

            userProfileRepository.delete(userProfile);
        }
        throw new UserProfileNotFoundException();
    }

    public String uploadImage(String email, MultipartFile image) throws IOException, UserProfileNotFoundException {

        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if (userProfile == null) {
            throw new UserProfileNotFoundException();
        }

        String cloudName = "kanban-cloud";
        String apiKey = "133434548116974";
        String apiSecret = "5BGhlk6_XD4zPhbEM6sle4JLVAw";

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));

        Map<String, String> result = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        userProfile.setProfilePictureUrl(result.get("secure_url"));
        userProfileRepository.save(userProfile);
        return result.get("secure_url");
    }
}
