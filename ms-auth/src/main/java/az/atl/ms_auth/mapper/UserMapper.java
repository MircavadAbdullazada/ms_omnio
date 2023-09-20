package az.atl.ms_auth.mapper;

import az.atl.ms_auth.dao.entity.UserEntity;
import az.atl.ms_auth.model.dto.UserDto;

import java.util.List;

public enum UserMapper {
    USER_MAPPER;

    public static UserEntity buildUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .jobTitle(userDto.getJobTitle())
                .build();
    }

    public static UserDto buildUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .userName(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .jobTitle(userEntity.getJobTitle())
                .build();
    }

    public static List<UserDto> buildDtoList(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserMapper::buildUserDto)
                .toList();
    }

}
