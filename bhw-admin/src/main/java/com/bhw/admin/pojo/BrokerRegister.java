package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("T_BORKER")
public class BrokerRegister {     //经纪人注册
    @TableId("ID")
    private Integer id;

    @TableField("PHOTO")
    private String photo;

    @TableField("PHONE")
    private String phone;

    @TableField("NAME")
    private String name;

    @TableField("SEX")
    private Integer sex;

    @TableField("EMAIL")
    private String email;

    @TableField("SENIORITY")
    private String seniority;     //从事年限

    @TableField("MAIN_ACHIEVE")
    private String mainAchieve;    //主要成就

    @TableField("STATUS")
    private Integer status;       //状态

    @TableField("BROKERID")
    private Integer borkerId;

    @TableField("USERID")
    private Integer userId;

    @TableField("RESULT")
    private String result;


    @TableField(exist = false)
    private Integer userType;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private Integer userStatus;

    @TableField(exist = false)
    private String brokerName;

    @TableField(exist = false)
    private String pushTime;   //推送时间

    @TableField(exist = false)
    private Integer grade;

    @TableField(exist = false)
    private Integer gradeCounts;

    @TableField(exist = false)
    private List<BrokerRegister> typeList;    //经纪人类型

    @TableField("PROFESSIONID")
    private Integer professionId;

    @TableField("TURNOVER")
    private Integer turnover;   //成交量

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;


}
