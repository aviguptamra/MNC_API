package com.nvc.scanbar.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="productseries")
public class ProductSeries {
    @Id
    private String productId;
    @Column(nullable = false)
    private String productSeriesUniqueId;
    @Column(nullable = false)
    private long pointsScored = 0;
    @Column(nullable = false)
    private boolean active = true;
    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String modifiedBy;
    @Column(nullable = false)
    private LocalDateTime modifiedDate;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;
    @Column(nullable = false)
    private String appVersion;
    @Column(nullable = false)
    private String deviceId;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "product_id", updatable = false, nullable = false)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
