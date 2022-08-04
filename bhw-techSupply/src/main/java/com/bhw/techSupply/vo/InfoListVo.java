package com.bhw.techSupply.vo;

import lombok.Data;

@Data
public class InfoListVo {
    private Integer id;
    private String serverName;//服务标题
    private String bussinessName;//业务类型名称
    private Integer checkType;//供需类别
    private String checkTypeName;//供需类别名称
    private Integer onPriceType;//计价方式（1填写价格，2面议）
    private String originalPrice;//原价
    private String price;//售价
    private String budget;//预算
    private String publisher;//发布人
    private String contactName;//联系人
    private String contactPhone;//联系方式
    private String contactUnit;//联系单位
    private String remark;//详情介绍
    private String keyWord;//关键字
    private String cityName;
    private String provinceName;
    private String address;//所在地
    private String createTime;//发布时间
    private Integer status;
    private Integer auditStatus;
    private Integer userId;
}
