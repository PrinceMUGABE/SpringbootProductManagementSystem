package com.auca.repository;

import com.auca.modal.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Custom queries for search
    List<Sale> findBySaleDate(Date date);

    List<Sale> findByAmount(double amount);

}
