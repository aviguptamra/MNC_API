package com.nvc.scanbar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="productScanned")
@Getter
@Setter
public class ProductScanned {
    @Id
    @Column(nullable = false)
    private String productId;
    @Column(nullable = false)
    private long pointsScored = 0;
    private String productDetails;
    @Column(name = "active", nullable = false)
    private boolean isActive = true;
    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String modifiedBy;
    @Column(nullable = false)
    private LocalDateTime modifiedDate;
    private String remarks;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;
    @Column(nullable = false)
    private String appVersion;
    @Column(nullable = false)
    private String deviceId;
}
