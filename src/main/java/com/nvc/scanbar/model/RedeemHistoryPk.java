package com.nvc.scanbar.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


public class RedeemHistoryPk implements Serializable {
    private String redeemHistoryId;

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
