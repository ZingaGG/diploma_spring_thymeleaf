package ru.gb.diploma.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.diploma.model.Product;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.repositories.iProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final iProductRepository productRepo;

    public List<Product> getAllProduct(){
        return productRepo.findAll();
    }

    public Product getProductByName(String name) throws ProductNotFoundException {
        return productRepo.findByName(name).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

}
