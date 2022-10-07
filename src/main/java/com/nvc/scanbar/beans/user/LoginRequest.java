package com.nvc.scanbar.beans.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String mobile;
    private String password;
}
