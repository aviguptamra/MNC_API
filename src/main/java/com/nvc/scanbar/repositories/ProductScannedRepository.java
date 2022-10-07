package com.nvc.scanbar.repositories;

import com.nvc.scanbar.model.ProductScanned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductScannedRepository extends JpaRepository<ProductScanned, String> {
    List<ProductScanned> findByCreatedBy(String userId);
}
