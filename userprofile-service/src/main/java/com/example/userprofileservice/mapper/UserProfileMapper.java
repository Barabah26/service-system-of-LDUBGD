package com.example.userprofileservice.mapper;

import com.example.userprofileservice.dto.UserProfileDtoResponse;
import com.example.userprofileservice.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class UserProfileMapper {
    public UserProfileDtoResponse convertToDto(UserProfile userProfile) {
        UserProfileDtoResponse response = new UserProfileDtoResponse();
        response.setFaculty(userProfile.getFaculty());
        response.setSpecialty(userProfile.getSpecialty());
        response.setDegree(userProfile.getDegree());
        response.setGroup(userProfile.getGroup());
        return response;
    }
}
