package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_BROKERADVISORY")
public class BrokerAdvisory {      //经纪人顾问
    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("BROKERID")
    private Integer brokerId;

    @TableField("CONTENT")
    private String content;

    @TableField("SENDTYPE")
    private Integer sendType;  //发送类型

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String brokerName;

    @TableField(exist = false)
    private String userPhoto;

    @TableField(exist = false)
    private String brokerPhoto;
}
