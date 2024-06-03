package ru.gb.diploma.model.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.diploma.model.CartItem;
import ru.gb.diploma.model.DTO.cartItem.CartItemDTO;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);

    @Mapping(source = "productName", target = "product.name")
    @Mapping(source = "price", target = "product.price")
    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);
}
