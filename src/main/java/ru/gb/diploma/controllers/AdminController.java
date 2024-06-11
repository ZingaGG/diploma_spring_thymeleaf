package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.diploma.model.DTO.product.ProductDTO;
import ru.gb.diploma.model.Product;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.model.utils.mappers.ProductMapper;
import ru.gb.diploma.services.ProductService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
        productService.saveProduct(productMapper.toProduct(productDTO));
        return "redirect:/home"; // перенаправление на список продуктов после добавления
    }

    @PostMapping("/delete-product/{name}")
    public String deleteProduct(@PathVariable String name){
        productService.deleteProductByName(name);
        return "redirect:/home"; // перенаправление на список продуктов после добавления
    }

    @GetMapping("/update-product/{name}")
    public String updateProductForm(@PathVariable String name, Model model){
        try {
            model.addAttribute("product", productMapper.toDTO(productService.getProductByName(name)));
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "update-product";
    }

    @PostMapping("/update-product")
    public String updateProduct(@ModelAttribute("product") ProductDTO productDTO, @RequestParam String currentName) {
        try {
            Product product = productService.getProductByName(currentName);
            productService.updateProduct(product, productDTO.getName(), productDTO.getPrice(), productDTO.getQuantity());
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/home"; // перенаправление на список продуктов после обновления
    }
}
