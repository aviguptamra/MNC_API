package com.nvc.scanbar.services;

import com.nvc.scanbar.beans.user.LoginRequest;
import com.nvc.scanbar.beans.user.UserHistoryResponse;
import com.nvc.scanbar.beans.user.UserProfile;
import com.nvc.scanbar.beans.user.UserRequest;
import com.nvc.scanbar.model.User;

import java.util.List;

public interface UserService {
    User registerUser(UserRequest user);
    User loginUser(LoginRequest User);

    List<UserProfile> getAllUsers();

    UserProfile getUserById(String userId);

    UserHistoryResponse getUserHistory(String userId);
}
