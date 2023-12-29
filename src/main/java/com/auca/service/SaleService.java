package com.auca.service;

import com.auca.modal.Sale;

import java.util.Date;
import java.util.List;

public interface SaleService {


    Sale saveSale(Sale sale);
    Sale updateSale(Sale sale);
    Sale findSaleById(Long saleId);
    List<Sale> getAllSales();
    void deleteSale(Long saleId);


    List<Sale> searchSalesByDate(Date date);

    List<Sale> searchSalesByAmount(double amount);

}
