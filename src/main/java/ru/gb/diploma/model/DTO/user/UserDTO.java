package ru.gb.diploma.model.DTO.user;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
}
