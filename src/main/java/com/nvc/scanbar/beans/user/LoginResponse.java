package com.nvc.scanbar.beans.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private UserProfile userProfile;
}

