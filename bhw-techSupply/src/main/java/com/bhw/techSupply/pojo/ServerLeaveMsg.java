package com.bhw.techSupply.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("T_SERVER_LEAVEMSG")
public class ServerLeaveMsg {
    @TableId("ID")
    private Integer id;

    @TableField("SERVER_ID")
    private Integer serverId;

    @TableField("SERVER_NAME")
    private String serverName;

    @TableField("USER_NAME")
    private String userName;

    @TableField("TYPE")
    private Integer type;

    @TableField("LEAVEMSG_NAME")
    private String leavemsgName;

    @TableField("LEAVEMSG_TYPE")
    private Integer leavemsgType;

    @TableField("CREATE_TIME")
    private String createTime;
}
