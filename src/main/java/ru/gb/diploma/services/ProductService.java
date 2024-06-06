package ru.gb.diploma.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.diploma.model.Product;
import ru.gb.diploma.model.User;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.repositories.iProductRepository;

import java.awt.color.ProfileDataException;
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

    @Transactional
    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    @Transactional
    public void deleteProductByName(String name){
        productRepo.deleteProductByName(name);
    }

    @Transactional
    public void updateProduct(Product product, String newName, double newPrice, int newQuantity) {
        if (newName != null && !newName.isEmpty()) {
            product.setName(newName);
        }
        if (newPrice >= 0) {
            product.setPrice(newPrice);
        }
        if (newQuantity >= 0) {
            product.setQuantity(newQuantity);
        }
        productRepo.save(product);
    }


}
