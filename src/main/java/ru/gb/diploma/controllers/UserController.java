package ru.gb.diploma.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.diploma.model.DTO.product.ProductDTO;
import ru.gb.diploma.model.User;
import ru.gb.diploma.model.utils.mappers.ProductMapper;
import ru.gb.diploma.services.ProductService;
import ru.gb.diploma.services.UserService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class UserController {

    private final UserService userService;
    private final ProductService productService;
    private final ProductMapper productMapper;


    // Отображает главную страницу с товарами и обеспечивает доступ к работе с профилем и балансом
    @GetMapping
    public String homePage(Model model){

        List<ProductDTO> productDTOList = productService.getAllProduct().stream().map(productMapper::toDTO).toList();

        model.addAttribute("products", productDTOList);

        return "home";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user, Model model){
        System.out.println(user.getRole());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/editProfile")
    public String editProfilePage(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "editProfile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam(required = false) String newEmail,
                                @RequestParam(required = false) String newFirstName,
                                @RequestParam(required = false) String newLastName) {
        userService.updateUserProfile(user, newEmail, newFirstName, newLastName);
        return "redirect:/home/profile";
    }

    // Обработчик добавление баланса пользователю

    @PostMapping("/profile/addFunds")
    public String addFunds(@AuthenticationPrincipal User user, @RequestParam BigDecimal amount) {
        userService.addFunds(user, amount);
        return "redirect:/home/profile";
    }

    // Обработка покупки

    @PostMapping("/profile/purchase")
    public String purchase(@AuthenticationPrincipal User user,
                           @RequestParam BigDecimal totalCost,
                           Model model) {
        boolean success = userService.purchase(user, totalCost);
        if (success) {
            return "redirect:/confirmation";
        } else {
            model.addAttribute("error", "Insufficient funds");
            return "cart";
        }
    }

}
