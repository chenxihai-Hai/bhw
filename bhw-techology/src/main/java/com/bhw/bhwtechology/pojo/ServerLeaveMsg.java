package com.bhw.bhwtechology.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_SERVER_LEAVEMSG")
public class ServerLeaveMsg {
    @TableId("ID")
    private Integer id;

    @TableField("SERVER_ID")
    private Integer serverId;

    @TableField("SERVER_USER_ID")
    private Integer serverUserId;

    @TableField("SERVER_NAME")
    private String serverName;

    @TableField("USERID")
    private Integer userId;

    @TableField("USER_NAME")
    private String userName;

    @TableField("TYPE")
    private Integer type;

    @TableField("LEAVEMSG_NAME")
    private String leavemsgName;

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("MSG")
    private String msg;

    @TableField("LEAVEMSG_TYPE")
    private Integer leavemsgType;

    @TableField(value="CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;
}
