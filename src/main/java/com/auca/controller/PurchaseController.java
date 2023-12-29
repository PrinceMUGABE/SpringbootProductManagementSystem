package com.auca.controller;

import com.auca.modal.Product;
import com.auca.modal.Sale;
import com.auca.service.ProductService;
import com.auca.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping("/admin/purchase")
public class PurchaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @GetMapping("/add")
    public String showPurchaseForm(@RequestParam Long productId, Model model) {
        Product product = productService.findProductById(productId);

        if (product == null) {
            model.addAttribute("message", "No such Product");
            return "redirect:/";
        }

        model.addAttribute("productId", productId);
        model.addAttribute("product", product);
        model.addAttribute("sale", new Sale());
        return "admin/sale/purchase-form";
    }

    @PostMapping("/save/new")
    public String processPurchaseForm(
            @ModelAttribute Sale sale,
            @RequestParam Long purchaseProductId,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String mobileMoneyPhoneNumber,
            @RequestParam(required = false) String creditCardNumber,
            RedirectAttributes ra) {

        if (purchaseProductId == null) {
            ra.addFlashAttribute("message", "No such Product");
            return "redirect:/";
        }

        // Retrieve the product from the product table using the product ID from the form
        Product product = productService.findProductById(purchaseProductId);


        // Check if the product exists
        if (product == null) {
            ra.addFlashAttribute("message", "No such Product");
            return "redirect:/";
        }

        // Get the product ID from the database
        Long databaseProductId = product.getId();

        // Check if the product ID from the form matches the product ID from the table
        if (!purchaseProductId.equals(databaseProductId)) {
            ra.addFlashAttribute("message", "Invalid Product ID");
            return "redirect:/";
        }


        sale.setProduct(product);
        sale.setProductId(product.getId()); // Set the product ID in the sale
        sale.setMobileMoneyPhoneNumber(mobileMoneyPhoneNumber);
        sale.setCreditCardNumber(creditCardNumber);
        sale.setAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)).doubleValue());
        sale.setSaleDate(new Date());
        sale.setQuantity(quantity);

        // Save the sale
        Sale savedSale = saleService.saveSale(sale);

        if (savedSale != null) {
            ra.addFlashAttribute("message", "Your purchase worked successfully");
        } else {
            ra.addFlashAttribute("message", "Fail to purchase! Try again.");
        }

        return "redirect:/";
    }

}
