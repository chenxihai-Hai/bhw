package com.bhw.admin.dto;


import lombok.Data;

@Data
public class ResourceAreaRangeReq {
    private Integer type;
    private Long provinceId;
    private Integer startCapital;
    private Integer endCapital;
    private Integer startDate;
    private Integer endDate;
    private String fullName;
    private String legalPerson;
}
