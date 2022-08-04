package com.bhw.bhwtechology.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_TECHOLOGY_INFO")
public class TechologyInfo {
    @TableId("ID")
    private Integer id;

    @TableField("CHECK_TYPE")
    private Integer checkType;

    @TableField("TECH_NAME")
    private String techName;

    @TableField("TECH_CODE")
    private String techCode;

    @TableField("BUSINESS_TYPE")
    private Integer businessType;

    @TableField("ON_PRICE_TYPE")
    private Integer onPriceType;

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

    @TableField(value="CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "CUTOFF_DATE")
    private String cutoffDate;

    @TableField("STATUS")
    private Integer status;

    @TableField("USERID")
    private Integer userId;

    @TableField("SOURCE")
    private String source;

    @TableField("AUDIT_STATUS")
    private Integer auditStatus;

    @TableField(exist = false)
    private String uId;

}
