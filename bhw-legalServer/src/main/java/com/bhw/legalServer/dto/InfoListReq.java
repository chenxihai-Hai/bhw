package com.bhw.legalServer.dto;

import lombok.Data;

@Data
public class InfoListReq {
    private Integer checkType;//供需列表
    private String publisher;//发布人
    private Integer auditStatus;
}
