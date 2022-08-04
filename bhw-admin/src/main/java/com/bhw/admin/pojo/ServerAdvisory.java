package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_SERVERADVISORY")
public class ServerAdvisory {     //服务咨询
    @TableId("id")
    private Integer id;

    @TableField("SERVERNAME")
    private String serverName;

    @TableField("SERVERTYPE")
    private Integer serverType;

    @TableField("SERVERREMARK")
    private String serverRemark;

    @TableField("USERNAME")
    private String userName;

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("STATUS")
    private Integer status;

    @TableField("DISPOSEID")
    private Integer disposeid;

    @TableField("BORKERID")
    private Integer borkerId;

    @TableField("DISPOSEREMARK")
    private String disposeRemark;

    @TableField(value="UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String borkerUserName;

    @TableField(exist = false)
    private String borkerUserId;

}
