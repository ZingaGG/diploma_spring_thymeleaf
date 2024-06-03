package ru.gb.diploma.model.utils.mappers;

import org.mapstruct.Mapper;
import ru.gb.diploma.model.Cart;
import ru.gb.diploma.model.DTO.cart.CartDTO;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {
    CartDTO toDTO(Cart cart);
    Cart toCart(CartDTO cartDTO);
}
