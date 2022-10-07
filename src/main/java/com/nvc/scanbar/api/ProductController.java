package com.nvc.scanbar.api;

import com.nvc.scanbar.beans.productseries.ProductSeriesRequest;
import com.nvc.scanbar.beans.productseries.ProductSeriesResponse;
import com.nvc.scanbar.beans.productseries.ScanProductRequest;
import com.nvc.scanbar.common.util.ScanbarUtil;
import com.nvc.scanbar.exceptions.DataConflictException;
import com.nvc.scanbar.exceptions.InternalServerError;
import com.nvc.scanbar.exceptions.NotFoundException;
import com.nvc.scanbar.exceptions.ValidationException;
import com.nvc.scanbar.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    String traceIdentifier = UUID.randomUUID().toString();

    @PostMapping("/productSeries")
    public ResponseEntity<ProductSeriesResponse> addProductSeries(@RequestBody ProductSeriesRequest productSeries) {
        try {
            if(ScanbarUtil.isValidProductSeries(productSeries.getProductSeriesUniqueId())) {
                return new ResponseEntity<>(productService.addProductSeries(productSeries), HttpStatus.CREATED);
            } else {
                throw new ValidationException("Product Series length cannot be greater than or less than 8!", traceIdentifier);
            }
        } catch (ValidationException e) {
            throw e;
        } catch (DataIntegrityViolationException constraintViolationException) {
            throw new DataConflictException("Product Series already added", traceIdentifier);
        } catch (Exception e) {
            throw new InternalServerError(traceIdentifier);
        }
    }

    @DeleteMapping("/productSeries/{productId}")
    public ResponseEntity<?> deleteProductSeries(@PathVariable("productId") String productId) {
        try {
            productService.deleteProductSeries(productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            throw new DataConflictException("Product series doesn't exist with given Product Id", traceIdentifier);
        } catch (Exception e) {
            throw new InternalServerError(traceIdentifier);
        }
    }

    @GetMapping("/productSeries")
    public ResponseEntity<List<ProductSeriesResponse>> getAllProductSeries() {
        List<ProductSeriesResponse> responses = productService.getAllProductSeries();
        if (responses == null || responses.isEmpty()) {
            throw new NotFoundException("Could not find any product.", traceIdentifier);
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/scan")
    public ResponseEntity<?> scanProduct(@RequestBody ScanProductRequest scanProductRequest) {
        try {
            productService.scanProduct(scanProductRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataConflictException e) {
           throw e;
        } catch (Exception e) {
            throw new InternalServerError(traceIdentifier);
        }
    }
}
