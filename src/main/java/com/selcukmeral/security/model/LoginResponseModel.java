package com.selcukmeral.security.model;

import java.util.Date;

import lombok.Data;

@Data
public class LoginResponseModel {
    private UserModel user;
    private String token;
    private Date expirationDate;

}
