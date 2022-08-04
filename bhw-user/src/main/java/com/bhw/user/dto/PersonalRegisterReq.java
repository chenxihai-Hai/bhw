package com.bhw.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 个人注册请求参数
 */
@Data
public class PersonalRegisterReq {
    private String phoneCaptcha;
    private String phone;
    private String pwd;
}
