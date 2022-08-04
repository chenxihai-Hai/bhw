package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_RESOURCE_AREA")
public class ResourceAreaBean {         //资源集聚区
    @TableId("ID")
    private Integer id;

    @TableField("FULL_NAME")
    private String fullName;

    @TableField("SHORT_NAME")
    private String shortName;

    @TableField("LEGAL_PERSON")
    private String legalPerson;

    @TableField("REGISTERED_CAPITAL")
    private String registeredCapital;    //注册资本

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("ADDRESS")
    private String address;

    @TableField("INDUSTEY")
    private String industey;    //行业

    @TableField("TYPE")
    private Integer type;

    @TableField("CITY_ID")
    private long cityId;

    @TableField("IMG")
    private String img;

    @TableField("PROFILE")
    private String profile;    //简介

    @TableField(value="CREATEDATE",fill = FieldFill.INSERT)
    private Date createDate;
}
