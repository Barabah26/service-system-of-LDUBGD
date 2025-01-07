package com.example.userprofileservice.service.impl;


import com.example.userprofileservice.dto.UserProfileDtoRequest;
import com.example.userprofileservice.dto.UserProfileDtoResponse;
import com.example.userprofileservice.entity.UserProfile;
import com.example.userprofileservice.mapper.UserProfileMapper;
import com.example.userprofileservice.repository.UserProfileRepository;
import com.example.userprofileservice.service.UserProfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public Optional<UserProfile> getProfileById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public UserProfileDtoResponse updateProfile(Long id, UserProfileDtoRequest userProfileData) {
        UserProfile existingProfile = userProfileRepository.findById(id).orElse(null);
        if (existingProfile == null) {
            return null;
        }

        existingProfile.setFaculty(userProfileData.getFaculty());
        existingProfile.setSpecialty(userProfileData.getSpecialty());
        existingProfile.setDegree(userProfileData.getDegree());
        existingProfile.setGroup(userProfileData.getGroup());

        UserProfile updatedProfile = userProfileRepository.save(existingProfile);
        return userProfileMapper.convertToDto(updatedProfile);
    }
}
