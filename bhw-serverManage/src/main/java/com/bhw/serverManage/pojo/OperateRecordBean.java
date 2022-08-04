package com.bhw.serverManage.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_OPERATE_RECORD")
public class OperateRecordBean {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("TITLE")
    private String title;

    @TableField("TYPE")
    private Integer type;

    @TableField("CONTENT")
    private String content;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
