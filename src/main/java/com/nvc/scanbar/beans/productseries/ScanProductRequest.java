package com.nvc.scanbar.beans.productseries;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScanProductRequest {
    private String productId;
    private String latitude;
    private String longitude;
    private String remarks;
    private String deviceId;
    private String appVersion;
    private String productDetails;
}
