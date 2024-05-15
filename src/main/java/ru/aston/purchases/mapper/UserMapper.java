package ru.aston.purchases.mapper;

import org.mapstruct.Mapper;
import ru.aston.purchases.dto.UserDto;
import ru.aston.purchases.dto.UserOutDto;
import ru.aston.purchases.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserDto userDTO);

    UserDto toDTO(User user);

    UserOutDto toOutDTO(User user);

    List<User> toListModels(List<UserDto> userDTOList);

    List<UserDto> toListDTO(List<User> userList);
}
