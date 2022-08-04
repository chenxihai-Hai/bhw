package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_LISTED_TRANSTION")
public class ListedTranstion {     //交易列表

    @TableId("ID")
    private Integer id;

    @TableField("LISTED_NUMBER")
    private String number;

    @TableField("LISTED_NAME")
    private String name;

    @TableField("CLIENT")
    private String client;

    @TableField("DELISTER")
    private String delister;

    @TableField("STATUS")
    private Integer status;

    @TableField("CLOSESTATUS")
    private Integer closeStatus;

    @TableField("START_TIME")
    private String startTime;

    @TableField("END_TIME")
    private String endTime;

}
