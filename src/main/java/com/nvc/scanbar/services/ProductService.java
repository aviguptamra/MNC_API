package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.productseries.ProductSeriesRequest;
import com.nvc.scanbar.beans.productseries.ProductSeriesResponse;
import com.nvc.scanbar.beans.productseries.ScanProductRequest;

import java.util.List;

public interface ProductService {

    ProductSeriesResponse addProductSeries(ProductSeriesRequest productSeries);

    void deleteProductSeries(String productSeriesId);

    List<ProductSeriesResponse> getAllProductSeries();

    boolean scanProduct(ScanProductRequest scanProductRequest);
}
