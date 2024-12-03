package com.example.authservice.mapper;

import com.example.authservice.dto.UserDtoResponse;
import com.example.authservice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperResponse extends Mappable<User, UserDtoResponse> {

    @Override
    User toEntity(UserDtoResponse dto);

    @Override
    UserDtoResponse toDto(User entity);

    @Override
    List<UserDtoResponse> toDto(List<User> entity);
}
