package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.diploma.model.User;
import ru.gb.diploma.services.CartService;
import ru.gb.diploma.services.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CartService cartService;

    @RequestMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.registerUser(user);
        cartService.createCart(user);
        return "redirect:/login";
    }
}
