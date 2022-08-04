package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_USER_SYS_MSG")
public class UserSysMsg {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("MSGTYPE")
    private Integer msgType;

    @TableField("PRODUCTID")
    private Integer productId;

    @TableField("CONTENT")
    private String content;

    @TableField("STATUS")
    private Integer status;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
