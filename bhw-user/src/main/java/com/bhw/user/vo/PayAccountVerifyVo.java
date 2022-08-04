package com.bhw.user.vo;

import lombok.Data;

@Data
public class PayAccountVerifyVo {
    private String phone;
    private String oldPassword;
    private Integer bankType;
    private String bankCard;
    private String idCard;
    private String userId;
}
