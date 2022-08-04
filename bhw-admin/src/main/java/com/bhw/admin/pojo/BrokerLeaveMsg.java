package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_BROKER_LEAVEMSG")
public class BrokerLeaveMsg {      //经纪人留言
    @TableId("id")
    private Integer id;

    @TableField("BROKERID")
    private Integer brokerId;

    @TableField("CONTENT")
    private String content;

    @TableField("USERNAME")
    private String userName;

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("REQUIREMSG")
    private String requireMsg;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
