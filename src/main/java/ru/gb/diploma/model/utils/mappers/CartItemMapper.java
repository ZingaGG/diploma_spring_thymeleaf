package ru.gb.diploma.model.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.gb.diploma.model.CartItem;
import ru.gb.diploma.model.DTO.cartItem.CartItemDTO;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "cartItem", target = "totalPrice", qualifiedByName = "getTotalPrice")
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);

    @Named("getTotalPrice")
    static double getTotalPrice(CartItem cartItem) {
        return cartItem.getTotalPrice();
    }
    @Mapping(source = "productName", target = "product.name")
    @Mapping(source = "price", target = "product.price")
    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);
}
