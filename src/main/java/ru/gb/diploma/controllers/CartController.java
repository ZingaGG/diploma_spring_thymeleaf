package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.diploma.model.Cart;
import ru.gb.diploma.model.DTO.cart.CartDTO;
import ru.gb.diploma.model.User;
import ru.gb.diploma.model.utils.exceptions.CartNotFoundException;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.model.utils.mappers.CartMapper;
import ru.gb.diploma.services.CartItemService;
import ru.gb.diploma.services.CartService;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CartItemService cartItemService;
    private final CartMapper cartMapper;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal User user, Model model) {
        CartDTO cart = null;
        try {
            cart = cartMapper.toDTO(cartService.getCart(user));
        } catch (CartNotFoundException e) {
            System.out.println(e.getMessage());
        }

        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add")
    public String addItemToCart(@AuthenticationPrincipal User user,
                                @RequestParam String productName,
                                @RequestParam int quantity) {


        try {
            cartService.getCart(user);
            cartService.addItemToCart(user, productName, quantity);
        } catch (CartNotFoundException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@AuthenticationPrincipal User user,
                                     @RequestParam Long itemId) {
        try {
            cartService.removeItemFromCart(user, itemId);
        } catch (CartNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal User user) {
        try {
            cartService.clearCart(user);
        } catch (CartNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/cart";
    }

    // Обрабатывает запрос на увелечение продукта в корзине на 1

    @PostMapping("/incrementQuantity")
    public String incrementQuantity(@AuthenticationPrincipal User user,
                                    @RequestParam String productName) {
        try {
            cartService.incrementQuantity(user, productName);
        } catch (CartNotFoundException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/cart";
    }

    // Обрабатывает запрос на уменьшение продукта в корзине на 1

    @PostMapping("/decrementQuantity")
    public String decrementQuantity(@AuthenticationPrincipal User user,
                                    @RequestParam String productName) {
        try {
            cartService.decrementQuantity(user, productName);
        } catch (CartNotFoundException | ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/cart";
    }
}
