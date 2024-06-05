package ru.gb.diploma.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

// Ентити корзины

@Entity
@Data
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", orphanRemoval = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    @Column(name = "total_cost")
    private BigDecimal totalCost = BigDecimal.valueOf(0);

    public void addItem(CartItem item){
        BigDecimal price = BigDecimal.valueOf(item.getProduct().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()));
        this.totalCost = price;
        this.cartItems.add(item);
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
