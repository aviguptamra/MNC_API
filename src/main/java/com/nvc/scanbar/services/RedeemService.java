package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.redeemhistory.RedeemRequest;

import java.util.List;

public interface RedeemService {
    boolean redeemPoints(RedeemRequest redeemRequest);
}
