package com.bhw.admin.dto;

import lombok.Data;

@Data
public class ListedAdvisoryReq {
    private String remark;
    private String name;
    private String phone;
    private String captchaCode;
}
