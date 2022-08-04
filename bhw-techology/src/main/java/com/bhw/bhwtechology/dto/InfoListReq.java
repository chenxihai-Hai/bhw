package com.bhw.bhwtechology.dto;

import lombok.Data;

@Data
public class InfoListReq {
    private Integer checkType;//供需选择
    private Integer  businessType;//供需类别
    private Integer  intellectualType;//行业类别
    private String techName;//技术名称
    private Integer auditStatus;
}
