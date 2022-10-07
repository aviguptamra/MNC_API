package com.nvc.scanbar.beans.user;

import com.nvc.scanbar.beans.redeemhistory.RedeemHistoryResponse;
import com.nvc.scanbar.model.ProductScanned;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryResponse {
    private List<RedeemHistoryResponse> redeemResponses;
    private List<ProductScanned> productScannedList;
}
