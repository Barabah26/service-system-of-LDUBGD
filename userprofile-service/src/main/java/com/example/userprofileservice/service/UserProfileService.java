package com.example.userprofileservice.service;

import com.example.userprofileservice.dto.UserProfileDtoRequest;
import com.example.userprofileservice.dto.UserProfileDtoResponse;
import com.example.userprofileservice.entity.UserProfile;

import java.util.Optional;

public interface UserProfileService {
    Optional<UserProfile> getProfileById(Long id);
    UserProfileDtoResponse updateProfile(Long id, UserProfileDtoRequest userProfileData);
}
