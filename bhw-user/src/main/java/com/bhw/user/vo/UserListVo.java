package com.bhw.user.vo;

import lombok.Data;

@Data
public class UserListVo {
    private Integer id;
    private String userName;
    private Integer type;
    private Integer status;
    private String photo;
    private String sex;
    private String email;
    private String phone;
    private String name;
}
