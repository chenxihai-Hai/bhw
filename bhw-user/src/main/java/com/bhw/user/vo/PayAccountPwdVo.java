package com.bhw.user.vo;

import lombok.Data;

@Data
public class PayAccountPwdVo {
    private  String phone;
    private String userId;
    private String newPassword;
    private Integer bankType;
    private Integer bankCardType;
    private String bankCard;
    private String idCard;
    private String oldPassword;
}
