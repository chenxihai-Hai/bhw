package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_SERVER_HISTORY")
public class ServerHistory {
    @TableId("ID")
    private Integer id;

    @TableField("USER_ID")
    private Integer userId;

    @TableField("SERVER_ID")
    private Integer serverId;

    @TableField("SERVER_NAME")
    private String serverName;

    @TableField("IMG")
    private String img;

    @TableField("PATH")
    private String path;

    @TableField("PRICE")
    private String price;

    @TableField("TYPE")
    private Integer type;

    @TableField("OPERATE_TYPE")
    private Integer operateType;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private Integer counts;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String date;
}
