package com.bhw.bhwtechology.dto;

import lombok.Data;

@Data
public class ReleaseReq {
    private Integer checkType;//供需选择（1供，2需）
    private String techName;//技术名称
    private String techCode;//技术编码
    private Integer businessType;//业务类型
    private Integer industryType;//行业类别
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
    private String source;//数据来源
    private String serverName;//发布服务名称
    private String serverRemark;//介绍
    private Integer status;//1.保存草稿，2.发布,3下架
    private String cutoffDate;//截止日期
    private Integer userId;
}
