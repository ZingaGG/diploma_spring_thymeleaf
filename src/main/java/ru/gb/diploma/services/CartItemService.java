package ru.gb.diploma.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.diploma.model.CartItem;
import ru.gb.diploma.repositories.iCartItemRepository;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final iCartItemRepository repository;

    // Сохранить CartItem

    @Transactional
    public CartItem saveCartItem(CartItem cartItem){
        return repository.save(cartItem);
    }

    // Удалить CartItem

    @Transactional
    public void deleteCartItem(CartItem cartItem){
        repository.delete(cartItem);
    }
}
