package com.bhw.serverManage.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_REQUIREADVISORY")
public class RequireAdvisory {
    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("MERCHANTID")
    private Integer merchantId;

    @TableField("CONTENT")
    private String content;

    @TableField("SENDTYPE")
    private Integer sendType;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String merchantName;

    @TableField(exist = false)
    private String userPhoto;

    @TableField(exist = false)
    private String merchantPhoto;
}
