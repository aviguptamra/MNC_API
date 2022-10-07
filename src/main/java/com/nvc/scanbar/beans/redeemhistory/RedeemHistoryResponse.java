package com.nvc.scanbar.beans.redeemhistory;

import com.nvc.scanbar.model.RedeemHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedeemHistoryResponse {
    private String redeemUser;
    private String createdBy;
    private String organizationName;
    private RedeemHistory redeemHistory;
}
