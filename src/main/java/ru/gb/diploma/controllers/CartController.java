package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.diploma.model.Cart;
import ru.gb.diploma.model.CartItem;
import ru.gb.diploma.model.DTO.cart.CartDTO;
import ru.gb.diploma.model.User;
import ru.gb.diploma.model.utils.exceptions.CartNotFoundException;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.model.utils.mappers.CartItemMapper;
import ru.gb.diploma.model.utils.mappers.CartMapper;
import ru.gb.diploma.services.CartItemService;
import ru.gb.diploma.services.CartService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;
    private final CartMapper cartMapper;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal User user, Model model) {
        try {

            Cart cartForSort = cartService.getCart(user);

            // Сортировка, чтобы продукты не прыгали в корзине
            Set<CartItem> sortedCartItems = cartForSort.getCartItems().stream()
                    .sorted((item1, item2) -> item1.getProduct().getName().compareToIgnoreCase(item2.getProduct().getName()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            cartForSort.setCartItems(sortedCartItems);

            CartDTO cart = cartMapper.toDTO(cartForSort);


            double totalCost = cart.getCartItems().stream()
                    .map(cartItemMapper::cartItemDTOToCartItem)
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();

            model.addAttribute("cart", cart);
            model.addAttribute("totalCost", totalCost);
        } catch (CartNotFoundException e) {
            System.out.println(e.getMessage());
        }

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

    // Метод покупки с выдачей ошибки на фронт

    @PostMapping("/purchase")
    public String purchase(@RequestParam double totalCost,
                           @AuthenticationPrincipal User user,
                           Model model) {
        try {
            cartService.purchase(totalCost, user);
            return "thankPage";
        } catch (Exception e) {
            Cart cartForSort = null;
            try {
                cartForSort = cartService.getCart(user);
            } catch (CartNotFoundException ex) {
                cartService.createCart(user);
                return "redirect:/cart";
            }
            CartDTO cart = cartMapper.toDTO(cartForSort);
            model.addAttribute("cart", cart);
            model.addAttribute("totalCost", totalCost);
            model.addAttribute("error", "Insufficient funds");
            System.out.println(e.getMessage());
            return "cart";
        }
    }

    // Страница после успешной покупки
    @GetMapping("/thankPage")
    public String thankPage(){
        return "thankPage";
    }

}
