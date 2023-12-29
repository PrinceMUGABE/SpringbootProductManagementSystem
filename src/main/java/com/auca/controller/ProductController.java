package com.auca.controller;

import com.auca.modal.Product;
import com.auca.serviceImplementation.ProductServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductServiceImplementation productService;

    @Autowired
    public ProductController(ProductServiceImplementation productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String displayProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/product/list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/add";
    }


    @PostMapping("/add/new")
    public String addProduct(
            @Valid @ModelAttribute("product") Product product,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes
    ) {
        if (imageFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select an image file.");
            return "redirect:/admin/products/add";
        }

        try {
            // Set the image content
            product.setImage(imageFile.getBytes());

            // Save the product with the image
            productService.saveProduct(product);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error processing the uploaded file.");
            return "redirect:/admin/products/add";
        }

        return "redirect:/admin/products/list";
    }




    @GetMapping("/edit/{productId}")
    public String showEditProductForm(@PathVariable Long productId, Model model) {
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);
        return "admin/product/edit";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile,
                              RedirectAttributes ra) {
        // Process image file
        if (!imageFile.isEmpty()) {
            try {
                // Set the new image content
                product.setImage(imageFile.getBytes());
                // Set the new image type
                product.setImageType(imageFile.getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.updateProduct(product);
        ra.addFlashAttribute("message", "Product updated successfully");
        return "redirect:/admin/products/list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, RedirectAttributes ra) {
        productService.deleteProduct(productId);
        ra.addFlashAttribute("message", "product deleted successfully");
        return "redirect:/admin/products/list";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.isEmpty()) {
            List<Product> products = productService.searchProductsByName(name);
            model.addAttribute("products", products);
            return "admin/product/list";
        } else {
            model.addAttribute("message", "Please enter a valid product name to search.");
            return "admin/product/list";
        }
    }


    @GetMapping("/adminDashboard")
    public String getAdminDahboard(){
        return "adminDashboard";
    }





    @GetMapping("/image/{productId}")
    @ResponseBody
    public ResponseEntity<byte[]> serveFile(@PathVariable Long productId) {
        Product product = productService.findProductById(productId);
        if (product != null && product.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.parseMediaType(product.getImageType()));
            return new ResponseEntity<>(product.getImage(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
