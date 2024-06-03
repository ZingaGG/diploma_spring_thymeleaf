package ru.gb.diploma.model.DTO.cart;

import lombok.Data;
import ru.gb.diploma.model.DTO.cartItem.CartItemDTO;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDTO {
    private Long id;
    private BigDecimal totalCost = BigDecimal.valueOf(0);

    private Set<CartItemDTO> cartItems;
}
