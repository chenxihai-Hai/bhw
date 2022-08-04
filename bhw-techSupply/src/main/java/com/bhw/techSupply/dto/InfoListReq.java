package com.bhw.techSupply.dto;

import lombok.Data;

@Data
public class InfoListReq {
    private Integer checkType;//供需列表
    private String publisher;//发布人
    private Integer bussinessType;//业务类型
    private Integer auditStatus;
}
