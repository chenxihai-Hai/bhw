package com.bhw.user.dto;

import lombok.Data;

@Data
public class EditUserReq {
    private Integer id;
    private String userName;
    private String phoneNo;
    private String photo;
    private String emall;
    private Integer sex;
}
