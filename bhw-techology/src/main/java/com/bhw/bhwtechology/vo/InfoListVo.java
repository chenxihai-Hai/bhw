package com.bhw.bhwtechology.vo;

import lombok.Data;

@Data
public class InfoListVo {
    private Integer id;//标志id
    private String techCode;//编码
    private String techName;//技术名称
    private Integer checkType;//供需类别
    private String checkTypeName;//供需类别名称
    private Integer onPriceType;//计价方式（1填写价格，2面议）
    private String originalPrice;//原价
    private String price;//售价
    private String budget;//预算
    private String businessName;//供需类别名称
    private String intellectualName;//行业类别名称
    private String publisher;//发布人
    private String contactName;//联系人
    private String contactPhone;//联系方式
    private String contactUnit;//联系单位
    private String cutoffDate;//截至日期
    private String keyWord;//关键字
    private String cityName;
    private String provinceName;
    private String address;//所在地
    private Integer status;
    private Integer auditStatus;
    private Integer userId;
}
