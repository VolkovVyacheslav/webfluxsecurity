package com.volkov.webfluxsecurity.mappers;


import com.volkov.webfluxsecurity.dto.UserDto;
import com.volkov.webfluxsecurity.entity.UserEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(UserEntity userEntity);

    UserEntity map(UserDto userDto);
}
