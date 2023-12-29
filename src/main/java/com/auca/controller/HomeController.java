package com.auca.controller;

import com.auca.modal.Product;
import com.auca.serviceImplementation.ProductServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ProductServiceImplementation productService;

    @Autowired
    public HomeController(ProductServiceImplementation productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String displayHome(@RequestParam(defaultValue = "0") int page, Model model) {
        // Set the number of products per page
        int pageSize = 10;

        // Fetch a page of products
        Page<Product> pagedProducts = productService.getAllProductsPaged(PageRequest.of(page, pageSize));

        // Add the pagedProducts to the model
        model.addAttribute("pagedProducts", pagedProducts);

        return "Home";
    }
}

