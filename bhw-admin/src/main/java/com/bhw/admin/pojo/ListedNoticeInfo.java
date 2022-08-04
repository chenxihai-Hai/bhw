package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_LISTED_NOTICEINFO")
public class ListedNoticeInfo {   //通知列表
    @TableId("INFO_ID")
    private Integer infoId;

    @TableField("LISTED_NUMBER")
    private String number;

    @TableField("INFO_TITLE")
    private String infoTitle;   //通知标题

    @TableField("CONTENT")
    private String content;

    @TableField("TRANSACTION_TIME")
    private Date transactionTime;   //交易时间

    @TableField("TPT_TIME")
    private String tptTime;

    @TableField("TPT_POSTION")
    private String tptPostion;

    @TableField("CONTACT")
    private String contact;

    @TableField("REMARK")
    private String remark;

    @TableField("DELISTER")
    private String delister;
}
