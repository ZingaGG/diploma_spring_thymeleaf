package ru.gb.diploma.model;

import jakarta.persistence.*;
import lombok.Data;

// Адаптер между продуктом и корзиной

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
