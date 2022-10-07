package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.CommonSessionBean;
import com.nvc.scanbar.beans.redeemhistory.RedeemRequest;
import com.nvc.scanbar.exceptions.DataConflictException;
import com.nvc.scanbar.exceptions.NotFoundException;
import com.nvc.scanbar.model.RedeemHistory;
import com.nvc.scanbar.model.User;
import com.nvc.scanbar.repositories.RedeemHistoryRepository;
import com.nvc.scanbar.repositories.UserRepository;
import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.automapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RedeemServiceImpl implements RedeemService {

    @Autowired
    RedeemHistoryRepository redeemHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonSessionBean commonSessionBean;
    String traceIdentifier = UUID.randomUUID().toString();

    @Override
    public boolean redeemPoints(RedeemRequest redeemRequest) {
        User user = userRepository.getUserByUserId(redeemRequest.getUserId());
        if (user == null) {
            throw new NotFoundException("Requested user is not found.", traceIdentifier);
        }
        if (redeemRequest.getPointsRedeemed() > 0) {
            if (user.getUserPoints() >= redeemRequest.getPointsRedeemed()) {
                IMapper imapper = new AutoMapper();
                RedeemHistory redeemHistory = imapper.map(redeemRequest, RedeemHistory.class);
                redeemHistory.setCreatedBy(commonSessionBean.getUser().getUserId());
                redeemHistory.setModifiedBy(commonSessionBean.getUser().getUserId());
                redeemHistory.setCreatedDate(LocalDateTime.now());
                redeemHistory.setModifiedDate(LocalDateTime.now());
                redeemHistoryRepository.save(redeemHistory);
                user.setUserPoints(user.getUserPoints() - redeemRequest.getPointsRedeemed());
                user.setModifiedDate(LocalDateTime.now());
                userRepository.save(user);
                return true;
            } else {
                throw new DataConflictException("You cannot redeem points more than user points balance!!", traceIdentifier);
            }
        } else {
            throw new DataConflictException("You cannot redeem 0 Points.", traceIdentifier);
        }
    }
}
