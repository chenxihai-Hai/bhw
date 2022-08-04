package com.bhw.serverManage.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_BIGDATABQDET")
public class BigDataBqDet {

    @TableId("id")
    private Integer id;

    @TableField("BID")
    private String bid;

    @TableField("CONTENT")
    private String content;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
