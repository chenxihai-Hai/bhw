package com.bhw.admin.pojo;

import lombok.Data;

@Data
public class UserVo {

    private String userName;

    private String photo;

    private String duTyName;

    private String professionName;

    private String brokerName;

    public UserVo(String userName, String photo, String duTyName, String professionName, String brokerName) {
        this.userName = userName;
        this.photo = photo;
        this.duTyName = duTyName;
        this.professionName = professionName;
        this.brokerName = brokerName;
    }
}
