package ru.gb.diploma.model.DTO.user;


import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String firstName;
    private List<Long> orderIds;
}
