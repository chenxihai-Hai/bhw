package com.bhw.admin.vo;

import lombok.Data;

@Data
public class ListedMessageVo {
    private Integer infoId;
    private String number;
    private String  name;
    private String client;
    private String status;
    private Integer timeLeft;//剩余时间
}
