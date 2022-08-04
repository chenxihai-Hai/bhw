package com.bhw.legalServer.dto;

import lombok.Data;

@Data
public class ReleaseReq {
    private Integer checkType;//供需选择（1供，2需）
    private Integer businessType;//业务类型
    private Integer onPriceType;//计价方式
    private String originalPrice;//原价
    private String price;//售价
    private String budget;//预算
    private Long cityId;//城市id
    private String address;//详细地址
    private Integer freightType;//运费类型（1包邮，2不包邮）
    private String contactName;//联系人
    private String contactUnit;//联系人单位
    private String contactPhone;//联系人手机号
    private String keyWord;//搜索关键字
    private String serverName;//发布服务名称
    private String serverRemark;//介绍
    private Integer status;//1.保存草稿，2.发布
    private String source;
    private Integer userId;
    private Integer auditStatus;//审核
}
