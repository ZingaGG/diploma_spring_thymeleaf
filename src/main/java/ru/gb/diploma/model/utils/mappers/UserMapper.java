package ru.gb.diploma.model.utils.mappers;

import org.mapstruct.Mapper;
import ru.gb.diploma.model.DTO.user.UserDTO;
import ru.gb.diploma.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toUser(UserDTO user);
}
