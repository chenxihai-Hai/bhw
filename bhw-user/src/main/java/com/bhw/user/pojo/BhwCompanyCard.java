package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "T_BHWCOMPANY_CARD")
public class BhwCompanyCard extends Model {
    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("UNITECODE")
    private  String uniteCode;

    @TableField("CONTACTPHONE")
    private  String contactPhone;

    @TableField("BUSINESSLICENSE")
    private  String businessLicense;

    @TableField("AGENCYCODE")
    private  String agentCode;

    @TableField("TAXREGISTERCODE")
    private  String taxRegisterCode;

    @TableField("CORPORATEACCOUNT")
    private String corporateAccount;

    @TableField("TYPE")
    private  String type;

    @TableField("BUSINESSLICENSEURL")
    private  String businesslicenseurl;

    @TableField("OPERNAME")
    private  String opername;

    @TableField("OPERIDCARD")
    private  String operidcard;

    @TableField("COMPANYNAME")
    private  String companyName;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
