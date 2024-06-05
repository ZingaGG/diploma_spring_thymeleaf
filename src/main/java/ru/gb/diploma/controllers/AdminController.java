package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.diploma.model.DTO.product.ProductDTO;
import ru.gb.diploma.model.Product;
import ru.gb.diploma.model.utils.mappers.ProductMapper;
import ru.gb.diploma.model.utils.mappers.UserMapper;
import ru.gb.diploma.services.ProductService;
import ru.gb.diploma.services.UserService;

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
}
