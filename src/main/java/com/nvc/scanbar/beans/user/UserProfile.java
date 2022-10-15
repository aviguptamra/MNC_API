package com.nvc.scanbar.beans.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class UserProfile {
    private String userId;
    private String email;
    private String mobile;
    private String fname;
    private String lname;
    private String gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;
    private long userPoints;
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
    private String approverName;
    private String approverEmail;
    private String approverRemarks;
    private LocalDateTime lastActive;
    private String otp;
}
