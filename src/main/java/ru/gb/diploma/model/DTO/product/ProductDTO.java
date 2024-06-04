package ru.gb.diploma.model.DTO.product;

import lombok.Data;
import ru.gb.diploma.model.utils.ProductType;

@Data
public class ProductDTO {
    private String name;

    private double price;

    private int quantity;

    private ProductType productType;
}
