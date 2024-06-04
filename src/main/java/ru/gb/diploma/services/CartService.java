package ru.gb.diploma.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.diploma.model.Cart;
import ru.gb.diploma.model.CartItem;
import ru.gb.diploma.model.Product;
import ru.gb.diploma.model.User;
import ru.gb.diploma.model.utils.exceptions.CartNotFoundException;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.repositories.iCartRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final iCartRepository cartRepository;

    private final ProductService productService;

    private final CartItemService cartItemService;

    private final UserService userService;

    /**
     * Достает корзину из репозитория по пользователю
     * @param user
     * @return
     * @throws CartNotFoundException
     */
    public Cart getCart(User user) throws CartNotFoundException {
        return cartRepository.findByUser(user).orElseThrow(
                () -> new CartNotFoundException("Cart no found"));
    }

    /**
     * Определяет корзину пользователю корзину
     * @param user
     * @return
     */
    @Transactional
    public Cart createCart(User user){
        Cart newCart = new Cart();
        newCart.setUser(user);
        user.setCart(newCart);
        return cartRepository.save(newCart);
    }

    /**
     * Метод добавления продукта в корзину
     * @param user
     * @param productName
     * @param quantity
     * @throws CartNotFoundException
     * @throws ProductNotFoundException
     */
    @Transactional
    public void addItemToCart(User user, String productName, int quantity) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        Product product = productService.getProductByName(productName);

        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals(productName))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            // Проверка на случай если кол-во предметов в корзине привышает допустимое количество
            // Если такое произошло - ставит в поле quantity макс. кол-во продукта в наличии
            if(existingItem.getProduct().getQuantity() > existingItem.getQuantity() + quantity){
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartItemService.saveCartItem(existingItem);
            } else {
                existingItem.setQuantity(existingItem.getProduct().getQuantity());
                cartItemService.saveCartItem(existingItem);
            }

        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.addItem(cartItem);
            cartItemService.saveCartItem(cartItem);
        }
    }

    /**
     * Метод удаления кокретного продукта из корзины
     * @param user
     * @param itemId
     * @throws CartNotFoundException
     */

    @Transactional
    public void removeItemFromCart(User user, Long itemId) throws CartNotFoundException {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not Found"));
        if (cart != null) {
            Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                    .filter(item -> item.getId().equals(itemId))
                    .findFirst();
            if (cartItemOpt.isPresent()) {
                CartItem cartItem = cartItemOpt.get();
                cart.removeItem(cartItem);
                cartItemService.deleteCartItem(cartItem);
            }
        }
    }

    /**
     * Метод очищенеия всех продуктов из корзины
     * @param user
     * @throws CartNotFoundException
     */
    @Transactional
    public void clearCart(User user) throws CartNotFoundException {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not Found"));
        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    /**
     * Метод для работы счетчика кол-ва продукта внутри корзины (инкрементирование)
     * @param user
     * @param productName
     * @throws CartNotFoundException
     * @throws ProductNotFoundException
     */
    @Transactional
    public void incrementQuantity(User user, String productName) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        Product product = productService.getProductByName(productName);

        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals(productName))
                .findFirst();

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (cartItem.getQuantity() < product.getQuantity()) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemService.saveCartItem(cartItem);
            }
        }
    }

    /**
     * Метод для работы счетчика кол-ва продукта внутри корзины (декрментирование)
     * @param user
     * @param productName
     * @throws CartNotFoundException
     * @throws ProductNotFoundException
     */
    @Transactional
    public void decrementQuantity(User user, String productName) throws CartNotFoundException, ProductNotFoundException {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new CartNotFoundException("Cart not found"));
        Product product = productService.getProductByName(productName);

        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getName().equals(productName))
                .findFirst();

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemService.saveCartItem(cartItem);
            } else {
                removeItemFromCart(user, cartItem.getId());
            }
        }
    }
}
