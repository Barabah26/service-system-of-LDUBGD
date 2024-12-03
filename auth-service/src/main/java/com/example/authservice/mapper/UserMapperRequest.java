package com.example.authservice.mapper;

import com.example.authservice.dto.UserDtoRequest;
import com.example.authservice.dto.UserDtoResponse;
import com.example.authservice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapperRequest  extends Mappable<User, UserDtoRequest> {

    @Override
    User toEntity(UserDtoRequest dto);

    @Override
    UserDtoRequest toDto(User entity);

    @Override
    List<UserDtoRequest> toDto(List<User> entity);
}
