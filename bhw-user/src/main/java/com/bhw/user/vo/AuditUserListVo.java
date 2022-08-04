package com.bhw.user.vo;

import lombok.Data;

@Data
public class AuditUserListVo {
    private Integer id;
    private String userName;
    private String name;//真实姓名
    private String zheng;//身份证正面
    private String back;//身份证反面
    private String card;//身份证号
    private String companyname;//企业名称
    private String xinyuma;//信誉码
    private String picture;//营业执照扫描件
    private String operName;//企业联系人
    private String operIdCard;//联系人身份证号
    private String contactPhone;//联系人手机号
    private Integer merchantFlag;
}
