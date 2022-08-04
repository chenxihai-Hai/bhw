package com.bhw.techSupply.vo;

import lombok.Data;

@Data
public class ServerListPageVo {
    private Integer id;
    private String serverName;
    private String intellectualName;
    private String budget;
    private String serverRemark;
    private String businessName;
    private String provinceName;
    private String cityName;
    private Integer checkType;
    private Integer result;
    private Integer status;
}
