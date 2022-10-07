package com.nvc.scanbar.beans.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.util.Date;

@Getter
@Setter
public class UserRequest {
    private String email;
    private String mobile;
    @Transient
    private String password;
    private String fname;
    private String lname;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    private String organizationType;
    private String organizationName;
    private String address;
    private String address1;
    private String address2;
    private String state;
    private String district;
    private String pin;
    private String userType;
    private String latitude;
    private String longitude;
    private String appVersion;
    private String deviceId;
    private String approverName;
    private String approverEmail;
    private String approverRemarks;
}
