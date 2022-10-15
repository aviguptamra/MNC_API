package com.nvc.scanbar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="user")
@IdClass(UserPk.class)
@Getter @Setter
public class User {

    private String userId;
    @Column(nullable = false)
    private String fname;
    @Column(nullable = false)
    private String lname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String mobile;
    @Column(nullable = false)
    private long userPoints = 0;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private java.util.Date dob;
    @Column(nullable = false)
    private String organizationType;
    @Column(nullable = false)
    private String organizationName;
    @Column(nullable = false)
    private String address;
    private String address1;
    private String address2;
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String pin;
    @Column(nullable = false)
    private boolean isActive = false;
    @Column(nullable = false)
    private String userType;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    private String approverName;
    private String approverEmail;
    private String approverRemarks;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Column(nullable = false)
    private LocalDateTime modifiedDate;
    @Column(nullable = false)
    private String latitude;
    @Column(nullable = false)
    private String longitude;
    private String appVersion;
    private String deviceId;
    @Column(nullable = false)
    private LocalDateTime lastActive;
    private String otp;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userid", updatable = false, nullable = false)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
