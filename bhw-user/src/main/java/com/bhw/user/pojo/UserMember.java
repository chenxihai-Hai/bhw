package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_USER_MEMBER")
public class UserMember {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("TYPE")
    private Integer type;

    @TableField("STATUS")
    private Integer status;

    @TableField("INDATE")
    private String indate;

    @TableField("TOTALPRICE")
    private String totalPrice;

    @TableField("LASTORDERID")
    private String lastOrderId;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String nickName;

    @TableField(exist = false)
    private String phoneNo;

    @TableField(exist = false)
    private String email;

    @TableField(exist = false)
    private Integer sex;

    @TableField(exist = false)
    private String time;
}
