package az.atl.ms_auth.mapper;

import az.atl.ms_auth.model.Role;
import az.atl.ms_auth.model.dto.UserDto;
import az.atl.ms_auth.dao.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void testBuildUserEntity() {
        //Given
        UserDto userDto = new UserDto( "FirstName", "LastName", "UserName", "password", "user@gmail.com", "Job Title");

        //When
        UserEntity result = UserMapper.buildUserEntity(userDto);

        //Then
        assertAll(
                () -> assertEquals("FirstName", result.getFirstName()),
                () -> assertEquals("LastName", result.getLastName()),
                () -> assertEquals("UserName", result.getUsername()),
                () -> assertEquals("password", result.getPassword()),
                () -> assertEquals("user@gmail.com", result.getEmail()),
                () -> assertEquals("Job Title", result.getJobTitle())
        );
    }

    @Test
    void testBuildUserDto() {
        //Given
        UserEntity userEntity = new UserEntity(1L, "FirstName", "LastName", "UserName", "password", "user@gmail.com", "Job Title", Role.ADMIN);

        //When
        UserDto result = UserMapper.buildUserDto(userEntity);

        //Then
        assertAll(
                () -> assertEquals("FirstName", result.getFirstName()),
                () -> assertEquals("LastName", result.getLastName()),
                () -> assertEquals("UserName", result.getUserName()),
                () -> assertEquals("password", result.getPassword()),
                () -> assertEquals("user@gmail.com", result.getEmail()),
                () -> assertEquals("Job Title", result.getJobTitle())
        );

    }

    @Test
    void testBuildDtoList() {
        //Given
        List<UserEntity> userEntities = List.of(
                new UserEntity(1L, "FirstName 1", "LastName 1", "UserName 1", "password 1", "user1@gmail.com", "Job Title 1", Role.USER),
                new UserEntity(2L, "FirstName 2", "LastName 2", "UserName 2", "password 2", "user2@gmail.com", "Job Title 2", Role.AGENT)
        );

        //When
        List<UserDto> result = UserMapper.buildDtoList(userEntities);

        //Then
        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("FirstName 1", result.get(0).getFirstName()),
                () -> assertEquals("LastName 1", result.get(0).getLastName()),
                () -> assertEquals("UserName 1", result.get(0).getUserName()),
                () -> assertEquals("user1@gmail.com", result.get(0).getEmail()),
                () -> assertEquals("Job Title 1", result.get(0).getJobTitle()),
                () -> assertEquals("FirstName 2", result.get(1).getFirstName()),
                () -> assertEquals("LastName 2", result.get(1).getLastName()),
                () -> assertEquals("UserName 2", result.get(1).getUserName()),
                () -> assertEquals("user2@gmail.com", result.get(1).getEmail()),
                () -> assertEquals("Job Title 2", result.get(1).getJobTitle())
        );

    }

}
