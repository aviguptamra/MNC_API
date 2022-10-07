package com.nvc.scanbar.repositories;

import com.nvc.scanbar.model.ProductSeries;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


public interface ProductSeriesRepository extends JpaRepository<ProductSeries, String> {

    ProductSeries findByProductSeriesUniqueId(String productSeriesUniqueId);
    @Transactional
    void deleteByProductSeriesUniqueId(String productSeriesUniqueId);
}
