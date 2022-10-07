package com.nvc.scanbar.beans.redeemhistory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedeemRequest {
    private String userId;
    private long pointsRedeemed;
    private String latitude;
    private String longitude;
    private String remarks;
    private String deviceId;
    private String appVersion;
}
