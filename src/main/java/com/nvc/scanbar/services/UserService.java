package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.user.*;
import com.nvc.scanbar.model.User;

import java.util.List;

public interface UserService {
    User registerUser(UserRequest user);
    User loginUser(LoginRequest User);

    List<UserProfile> getAllUsers();

    UserProfile getUserById(String userId);

    UserHistoryResponse getUserHistory(String userId);

    void forgotPassword(VerificationRequest forgotPasswordRequest);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    void verifyEmail(VerificationRequest verificationRequest);
}
