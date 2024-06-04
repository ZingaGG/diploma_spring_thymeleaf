package ru.gb.diploma.model.utils.mappers;

import org.mapstruct.Mapper;
import ru.gb.diploma.model.DTO.product.ProductDTO;
import ru.gb.diploma.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toProduct(ProductDTO productDTO);
}
