package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.diploma.model.CartItem;
import ru.gb.diploma.model.User;
import ru.gb.diploma.model.utils.exceptions.CartNotFoundException;
import ru.gb.diploma.services.CartService;
import ru.gb.diploma.services.UserService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String checkCart(@PathVariable Long id) throws CartNotFoundException {
        cartService.getCart(userService.getUserById(id));
        return "Success";
    }

    @GetMapping("/user")
    public String checkUser(@AuthenticationPrincipal User user){
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        return "Success";
    }
}
