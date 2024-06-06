package ru.gb.diploma.model.DTO.cartItem;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
}
