package com.bhw.bhwtechology.vo;

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
    private String source;
    private Integer status;
}
