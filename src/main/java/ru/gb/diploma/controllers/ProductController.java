package ru.gb.diploma.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.diploma.model.DTO.ProductDTO;
import ru.gb.diploma.model.utils.exceptions.ProductNotFoundException;
import ru.gb.diploma.model.utils.mappers.ProductMapper;
import ru.gb.diploma.services.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    // Отображает карточку игрового продукта

    @GetMapping("/{name}")
    public String productCard(@PathVariable String name, Model model){

        try {
            ProductDTO product = productMapper.toDTO(productService.getProductByName(name));
            model.addAttribute("product", product);
        } catch (ProductNotFoundException e){
            System.out.println(e.getMessage());
        }




        return "product_card";
    }
}
