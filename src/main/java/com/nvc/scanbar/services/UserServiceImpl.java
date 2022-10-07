package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.redeemhistory.RedeemHistoryResponse;
import com.nvc.scanbar.beans.user.LoginRequest;
import com.nvc.scanbar.beans.user.UserHistoryResponse;
import com.nvc.scanbar.beans.user.UserProfile;
import com.nvc.scanbar.beans.user.UserRequest;
import com.nvc.scanbar.common.util.ScanbarUtil;
import com.nvc.scanbar.exceptions.AuthException;
import com.nvc.scanbar.exceptions.DataConflictException;
import com.nvc.scanbar.exceptions.NotFoundException;
import com.nvc.scanbar.model.ProductScanned;
import com.nvc.scanbar.model.User;
import com.nvc.scanbar.repositories.ProductScannedRepository;
import com.nvc.scanbar.repositories.RedeemHistoryRepository;
import com.nvc.scanbar.repositories.UserRepository;
import com.zhaofujun.automapper.AutoMapper;
import com.zhaofujun.automapper.IMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedeemHistoryRepository redeemHistoryRepository;

    @Autowired
    ProductScannedRepository productScannedRepository;

    IMapper mapper = new AutoMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    String traceIdentifier = UUID.randomUUID().toString();

    @Override
    public User registerUser(UserRequest user) {
        User userDo = mapper.map(user, User.class);
        userDo.setRegistrationDate(LocalDateTime.now());
        userDo.setLastActive(LocalDateTime.now());
        userDo.setCreatedDate(LocalDateTime.now());
        userDo.setModifiedDate(LocalDateTime.now());
        return userRepository.save(userDo);
    }

    @Override
    public User loginUser(LoginRequest user) {
        User userDo = null;
        if (user.getEmail() != null && !user.getEmail().isEmpty() && ScanbarUtil.isValidEmailString(user.getEmail())) {
            userDo = userRepository.getUserByEmail(user.getEmail());
            validateLogin(user, userDo);
        } else if (user.getMobile() != null && !user.getMobile().isEmpty() && ScanbarUtil.isValidMobileString(user.getMobile())) {
            userDo = userRepository.getUserByMobile(user.getMobile());
            validateLogin(user, userDo);
        } else {
            throw new AuthException("Either a valid email or mobile is required", traceIdentifier);
        }
        if (userDo != null && userDo.isActive()) {
            userDo.setLastActive(LocalDateTime.now());
            userRepository.save(userDo);
            return userDo;
        } else {
            throw new DataConflictException("User not active", traceIdentifier);
        }
    }

    @Override
    public List<UserProfile> getAllUsers() {
        List<UserProfile> response = null;
        List<User> itemList = userRepository.findAllByUserType("Public");
        if (!itemList.isEmpty()) {
            response = new ArrayList<>();
            for (User item : itemList) {
                UserProfile userProfile = mapper.map(item, UserProfile.class);
                userProfile.setLastActive(item.getModifiedDate());
                response.add(userProfile);
            }
        } else {
            throw new NotFoundException("No public user(s) found", traceIdentifier);
        }
        return response;
    }

    @Override
    public UserProfile getUserById(String userId) {
        User userDo = userRepository.getUserByUserId(userId);
        if (userDo == null) {
            throw new NotFoundException("User with the given user id not found. Please try with valid user id", traceIdentifier);
        }
        return mapper.map(userDo, UserProfile.class);
    }

    @Override
    public UserHistoryResponse getUserHistory(String userId) {
        List<RedeemHistoryResponse> redeemHistoryResponses = redeemHistoryRepository.getRedeemHistoryByUserId(userId);
        List<ProductScanned> productScannedList = productScannedRepository.findByCreatedBy(userId);
        if (redeemHistoryResponses != null && !redeemHistoryResponses.isEmpty() || productScannedList != null && !productScannedList.isEmpty()) {
            return new UserHistoryResponse(redeemHistoryResponses, productScannedList);
        } else {
            throw new NotFoundException("Could not find any history data!", traceIdentifier);
        }
    }

    private void validateLogin(LoginRequest loginRequest, User userDo) {
        if (userDo == null) {
            throw new AuthException("User not registered", traceIdentifier);
        } else if (!userDo.getPassword().equals(loginRequest.getPassword())) {
            throw new AuthException("Incorrect password", traceIdentifier);
        }
    }
}
