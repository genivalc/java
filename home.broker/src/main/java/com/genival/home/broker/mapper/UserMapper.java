package com.genival.home.broker.mapper;

import com.genival.home.broker.entities.User;
import com.genival.home.broker.requests.UserPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    public abstract User toUser(UserPostRequestBody userPostRequestBody);
}
