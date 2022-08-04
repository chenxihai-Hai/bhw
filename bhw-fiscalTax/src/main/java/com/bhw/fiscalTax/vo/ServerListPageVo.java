package com.bhw.fiscalTax.vo;

import lombok.Data;

@Data
public class ServerListPageVo {
    private Integer id;//服务id
    private String serverName;//服务名称
    private String intellectualName;//行业类别名称
    private String budget;//预算
    private String serverRemark;//简介
    private String businessName;//需求类型名称
    private String provinceName;//省份名
    private String cityName;//城市名
    private Integer checkType;//1供，2需
    private Integer result;//处理结果（0待解决，1已解决）
    private Integer status;
}
