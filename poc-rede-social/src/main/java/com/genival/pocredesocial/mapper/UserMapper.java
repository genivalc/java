package com.genival.pocredesocial.mapper;


import com.genival.pocredesocial.domain.User;
import com.genival.pocredesocial.requests.UserPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toUser(UserPostRequest userPostRequest);
}
