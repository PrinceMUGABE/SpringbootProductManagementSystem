package com.auca.controller;

import com.auca.modal.Product;
import com.auca.modal.Sale;
import com.auca.serviceImplementation.ProductServiceImplementation;
import com.auca.serviceImplementation.SaleServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

;
import java.util.List;

@Controller
@RequestMapping("/admin/sales")
public class SaleController {

    private final SaleServiceImplementation saleService;

    @Autowired
    public SaleController(SaleServiceImplementation saleService) {
        this.saleService = saleService;
    }
    @Autowired
    private ProductServiceImplementation productService;

    @GetMapping("/list")
    public String displaySales(Model model) {
        List<Sale> sales = saleService.getAllSales();
        model.addAttribute("sales", sales);
        return "admin/sale/list";
    }

    @GetMapping("/adminDashboard")
    public String getAdminDashboard(){
        return "adminDashboard";
    }

    @GetMapping("/add/new")
    public String showAddSaleForm(Model model) {
        model.addAttribute("sale", new Sale());
        return "admin/sale/add";
    }

    @PostMapping("/add")
    public String addSale(@ModelAttribute Sale sale) {
        saleService.saveSale(sale);
        return "redirect:/admin/sales";
    }

    @GetMapping("/edit/{saleId}")
    public String showEditSaleForm(@PathVariable Long saleId, Model model) {
        // Fetch the sale by ID
        Sale sale = saleService.findSaleById(saleId);

        // Fetch the list of products
        List<Product> products = productService.getAllProducts();

        // Add sale and products to the model
        model.addAttribute("sale", sale);
        model.addAttribute("products", products);

        return "admin/sale/edit";
    }

    @PostMapping("/save/edit")
    public String editSale(@ModelAttribute @Validated Sale sale,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            model.addAttribute("message", "Validation error. Please check the entered values.");
            return "admin/sale/edit";
        }

        // Fetch the existing sale from the database
        Sale existingSale = saleService.findSaleById(sale.getId());

        // Set the saleDate to the existing date
        sale.setSaleDate(existingSale.getSaleDate());

        // Update the sale
        saleService.updateSale(sale);

        return "redirect:/admin/sales/list";
    }





    @GetMapping("/delete/{saleId}")
    public String deleteSale(@PathVariable Long saleId) {
        saleService.deleteSale(saleId);
        return "redirect:/admin/sales/list";
    }

    @GetMapping("/search")
    public String searchSales(
            @RequestParam(required = false) Date date,
            @RequestParam(required = false) Long userID,
            @RequestParam(required = false) Double amount,
            Model model
    ) {
        List<Sale> sales = saleService.searchSalesByDate(date);
        sales.retainAll(saleService.searchSalesByAmount(amount));

        model.addAttribute("sales", sales);
        return "admin/sale/list";
    }

    @GetMapping("/add/new/sale")
    public String showSaleForm(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("sale", new Sale());
        model.addAttribute("products", products);
        return "admin/sale/addNew";
    }

    @PostMapping("/add/save/")
    public String saveSale(@ModelAttribute Sale sale) {
        saleService.saveSale(sale);
        return "redirect:/admin/sales/list";
    }
}
