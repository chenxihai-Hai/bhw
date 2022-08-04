package com.bhw.user.dto;

import lombok.Data;

@Data
public class UserListReq {
    private Integer userId;
    private String userName;
    private String phone;
    private String email;
    private Integer statusId;
    private String userType;//2普通用户，1商户用户
    private String sex;
    private String type;//1个人，2企业
}
