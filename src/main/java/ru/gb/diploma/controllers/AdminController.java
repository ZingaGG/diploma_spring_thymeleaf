package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.diploma.model.DTO.user.UserDTO;
import ru.gb.diploma.model.utils.mappers.UserMapper;
import ru.gb.diploma.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping("/users")
    public List<UserDTO> getAllUsers(){
        return userService.getListUsers()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
