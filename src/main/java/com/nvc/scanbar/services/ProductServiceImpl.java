package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.CommonSessionBean;
import com.nvc.scanbar.beans.productseries.ProductSeriesRequest;
import com.nvc.scanbar.beans.productseries.ProductSeriesResponse;
import com.nvc.scanbar.beans.productseries.ScanProductRequest;
import com.nvc.scanbar.exceptions.DataConflictException;
import com.nvc.scanbar.model.ProductScanned;
import com.nvc.scanbar.model.ProductSeries;
import com.nvc.scanbar.model.User;
import com.nvc.scanbar.repositories.ProductScannedRepository;
import com.nvc.scanbar.repositories.ProductSeriesRepository;
import com.nvc.scanbar.repositories.UserRepository;
import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.automapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductSeriesRepository productSeriesRepository;

    @Autowired
    ProductScannedRepository productScannedRepository;

    @Autowired
    CommonSessionBean commonSessionBean;

    @Autowired
    UserRepository userRepository;

    IMapper mapper = new AutoMapper();
    String traceIdentifier = UUID.randomUUID().toString();

    @Override
    public ProductSeriesResponse addProductSeries(ProductSeriesRequest productSeries) {
        IMapper mapper = new AutoMapper();
        ProductSeries productSeriesDo = mapper.map(productSeries, ProductSeries.class);
        productSeriesDo.setCreatedDate(LocalDateTime.now());
        productSeriesDo.setModifiedDate(LocalDateTime.now());
        return mapper.map(productSeriesRepository.save(productSeriesDo), ProductSeriesResponse.class);
    }

    @Override
    public void deleteProductSeries(String productId) {
        productSeriesRepository.deleteByProductSeriesUniqueId(productId);
    }

    @Override
    public List<ProductSeriesResponse> getAllProductSeries() {
        List<ProductSeriesResponse> response = null;
        List<ProductSeries> itemList = productSeriesRepository.findAll();
        if (!itemList.isEmpty()) {
            response = new ArrayList<>();
            for (ProductSeries item : itemList) {
                response.add(mapper.map(item, ProductSeriesResponse.class));
            }
        }
        return response;
    }

    @Override
    public boolean scanProduct(ScanProductRequest scanProductRequest) {
        ProductSeries productSeriesDo = new ProductSeries();
        List<ProductSeries> productSeriesList = productSeriesRepository.findAll();
        for (ProductSeries productSeries : productSeriesList) {
            if (scanProductRequest.getProductId().contains(productSeries.getProductSeriesUniqueId())) {
                if (productSeriesDo.getPointsScored() < productSeries.getPointsScored()) {
                    productSeriesDo = productSeries;
                }
            }
        }
        if (productSeriesDo.getProductId() == null) {
            throw new DataConflictException("Product code does not lie in the configured series by admin!!", traceIdentifier);
        }
        if (!productScannedRepository.existsById(scanProductRequest.getProductId())) {
            ProductScanned productScannedDo = mapper.map(scanProductRequest, ProductScanned.class);
            productScannedDo.setPointsScored(productSeriesDo.getPointsScored());
            productScannedDo.setCreatedBy(commonSessionBean.getUser().getUserId());
            productScannedDo.setModifiedBy(commonSessionBean.getUser().getUserId());
            productScannedDo.setCreatedDate(LocalDateTime.now());
            productScannedDo.setModifiedDate(LocalDateTime.now());
            productScannedRepository.save(productScannedDo);

            User user = userRepository.getUserByUserId(commonSessionBean.getUser().getUserId());
            user.setUserPoints(user.getUserPoints() + productSeriesDo.getPointsScored());
            user.setModifiedDate(LocalDateTime.now());
            userRepository.save(user);
        } else {
            throw new DataConflictException("Bar code of this product has been already scanned!!", traceIdentifier);
        }
        return true;
    }
}
