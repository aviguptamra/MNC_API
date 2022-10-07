package com.nvc.scanbar.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="redeemHistory")
@IdClass(RedeemHistoryPk.class)
@Getter
@Setter
public class RedeemHistory {
    private String redeemHistoryId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private long pointsRedeemed;
    @Column(nullable = false)
    private boolean isActive = true;
    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private String modifiedBy;
    @Column(nullable = false)
    private LocalDateTime modifiedDate;
    @Column(nullable = false)
    private String remarks;
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
    @Column(updatable = false, nullable = false)
    public String getRedeemHistoryId() {
        return redeemHistoryId;
    }

    public void setRedeemHistoryId(String redeemHistoryId) {
        this.redeemHistoryId = redeemHistoryId;
    }
}
