package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.xml.soap.SAAJResult;
import java.util.Date;

@Data
@TableName("T_SCHOOL")
public class SchoolBean {       //高校

    @TableId("ID")
    private Integer id;

    @TableField("NAME")
    private String name;

    @TableField("PHOTO")
    private String photo;

    @TableField("URL")
    private String url;

    @TableField("TYPE")
    private Integer type;

    @TableField("INTRODUCEIMG")
    private String introduceImg;

    @TableField("INTRODUCE")
    private String introduce;

    @TableField("CITY_ID")
    private Integer cityId;

    @TableField("ACADEMICIAN")
    private String academician;    //院士

    @TableField("ACADEMICIANIMG")
    private String academicianImg;

    @TableField("ACADEMICIANREMARK")
    private String academicianRemark;    //院士介绍

    @TableField("ACADEMICIANDUTY")
    private String academicianDuty;      //院士职务

    @TableField("ENNAME")
    private String enName;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String collegeProvince;

    @TableField(exist = false)
    private String collegeCity;
}
