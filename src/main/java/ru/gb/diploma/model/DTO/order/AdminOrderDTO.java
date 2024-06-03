package ru.gb.diploma.model.DTO.order;

import lombok.Data;
import ru.gb.diploma.model.DTO.user.UserDTO;

@Data
public class AdminOrderDTO {
    private Long id;
    private UserDTO userDTO;
}
