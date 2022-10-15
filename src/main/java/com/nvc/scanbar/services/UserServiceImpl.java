package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.redeemhistory.RedeemHistoryResponse;
import com.nvc.scanbar.beans.user.*;
import com.nvc.scanbar.common.util.ScanbarUtil;
import com.nvc.scanbar.exceptions.AuthException;
import com.nvc.scanbar.exceptions.DataConflictException;
import com.nvc.scanbar.exceptions.NotFoundException;
import com.nvc.scanbar.exceptions.ValidationException;
import com.nvc.scanbar.mail.EmailDetails;
import com.nvc.scanbar.mail.EmailService;
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

import java.time.LocalDateTime;
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
    @Autowired
    private EmailService emailService;

    IMapper mapper = new AutoMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    String traceIdentifier = UUID.randomUUID().toString();

    @Override
    public User registerUser(UserRequest user) {
        User userDo = mapper.map(user, User.class);
        emailService.sendSimpleMail(new EmailDetails(user.getEmail(), user.getOtp(), "OTP to verify email"));
        userDo.setUserType("Public");
        userDo.setActive(false);
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
            throw new ValidationException("Either a valid email or mobile is required", traceIdentifier);
        }
        if (userDo.isActive()) {
            userDo.setLastActive(LocalDateTime.now());
            userRepository.save(userDo);
            return userDo;
        } else {
            emailService.sendSimpleMail(new EmailDetails(user.getEmail(), userDo.getOtp(), "OTP to verify email"));
            throw new DataConflictException(userDo.getEmail()+","+userDo.getOtp(), traceIdentifier);
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

    @Override
    public void forgotPassword(VerificationRequest forgotPasswordRequest) {
        User user = userRepository.getUserByEmail(forgotPasswordRequest.getEmail());
        if (user != null) {
            user.setOtp(forgotPasswordRequest.getOtp());
            EmailDetails details = new EmailDetails(forgotPasswordRequest.getEmail(), forgotPasswordRequest.getOtp(), "OTP to reset the password");
            emailService.sendSimpleMail(details);
            userRepository.save(user);
        } else {
            throw new NotFoundException("User not found with given mail", traceIdentifier);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.getUserByEmail(resetPasswordRequest.getEmail());
        if(user != null) {
            if(resetPasswordRequest.getOtp().equals(user.getOtp())) {
                user.setPassword(resetPasswordRequest.getNewPassword());
                userRepository.save(user);
            } else {
                throw new DataConflictException("Provided OTP is incorrect. Please provide the correct one", traceIdentifier);
            }
        } else {
            throw new NotFoundException("User not found with given mail", traceIdentifier);
        }

    }

    @Override
    public void verifyEmail(VerificationRequest verificationRequest) {
        User user = userRepository.getUserByEmail(verificationRequest.getEmail());
        if(user!=null)
        {
            if(user.getOtp().equals(verificationRequest.getOtp())) {
                user.setActive(true);
                userRepository.save(user);
            } else {
                throw new AuthException("Provided OTP is incorrect. Please provide the correct one", traceIdentifier);
            }
        } else {
            throw new NotFoundException("User was not found with the given Email.", traceIdentifier);
        }
    }

    private void validateLogin(LoginRequest loginRequest, User userDo) {
        if (userDo == null) {
            throw new ValidationException("User not registered", traceIdentifier);
        } else if (!userDo.getPassword().equals(loginRequest.getPassword())) {
            throw new ValidationException("Incorrect password", traceIdentifier);
        }
    }
}
