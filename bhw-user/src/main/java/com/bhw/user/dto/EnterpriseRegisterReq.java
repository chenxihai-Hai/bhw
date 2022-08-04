package com.bhw.user.dto;

import lombok.Data;

import java.util.List;

/**
 * 企业注册请求参数
 */
@Data
public class EnterpriseRegisterReq {
    public String account; //登录账号
    public String companyName; //公司名称
    public String pwd; //密码
    public String companyPhone; //公司电话
    public String type; //是否三证合一（0是，1不是）
    public String uniteCode; //统一社会信用代码
    public String businessLicense; //营业执照
    public String agencyCode; //机构代码
    public String taxRegisterCode; //税务登记证
    public String contactPhone; //联系人电话
    public String phoneCaptcha; //手机验证码
    public Integer serverType01;
    public Integer serverType02;
}
