package com.auca.serviceImplementation;

import com.auca.modal.Sale;
import com.auca.repository.SaleRepository;
import com.auca.service.SaleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SaleServiceImplementation implements SaleService {

    @Autowired
    private SaleRepository saleRepository;
    private static final Logger log = LoggerFactory.getLogger(Sale.class);


    @Transactional
    @Override
    public Sale saveSale(Sale sale) {
        try {
            Sale savedSale = saleRepository.save(sale);
            log.info("Sale saved successfully: {}", savedSale);
            return savedSale;
        } catch (Exception e) {
            log.error("Error saving sale", e);
            throw e; // Rethrow the exception to trigger a rollback
        }
    }

    @Override
    public Sale updateSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Sale findSaleById(Long saleId) {
        Optional<Sale> optionalSale = saleRepository.findById(saleId);
        return optionalSale.orElse(null);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }

    @Override
    public List<Sale> searchSalesByDate(Date date) {
        return saleRepository.findBySaleDate(date);
    }


    @Override
    public List<Sale> searchSalesByAmount(double amount) {
        return saleRepository.findByAmount(amount);
    }

}
