package com.bhw.fiscalTax.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("T_FISCALTAX_INFO")
public class FiscalTaxInfo extends Model {
    @TableId("ID")
    private Integer id;

    @TableField("CHECK_TYPE")
    private Integer checkType;    //服务标题

    @TableField("BUSINESS_TYPE")
    private Integer businessType;     //业务类型

    @TableField("ON_PRICE_TYPE")
    private Integer onPriceType;      //供需类型

    @TableField("ORIGINAL_PRICE")
    private String originalPrice;

    @TableField("PRICE")
    private String price;

    @TableField("BUDGET")
    private String budget;

    @TableField("ADDRESS")
    private String address;

    @TableField("CITY_ID")
    private Long cityId;

    @TableField("FREIGHT_TYPE")
    private Integer freightType;

    @TableField("CONTACT_NAME")
    private String contactName;

    @TableField("CONTACT_UNIT")
    private String contactUnit;

    @TableField("CONTACT_PHONE")
    private String contactPhone;

    @TableField("KEY_WORD")
    private String keyWord;

    @TableField("INTELLECTUALTYPE")
    private Integer intellectualType;

    @TableField("SERVER_NAME")
    private String serverName;

    @TableField("SERVER_REMARK")
    private String serverRemark;

    @TableField("RESULT")
    private Integer result;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("STATUS")
    private Integer status;

    @TableField("AUDIT_STATUS")
    private Integer auditStatus;

    @TableField("SOURCE")
    private String source;

    @TableField("USERID")
    private Integer userId;

    @TableField(exist = false)
    private String businessName;//需求类型名称

    @TableField(exist = false)
    private String provinceName;//省份名

    @TableField(exist = false)
    private String cityName;//城市名

    @TableField(exist = false)
    private String intellectualName;//行业类别名称

    @TableField(exist = false)
    private String publisher;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
