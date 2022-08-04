package com.bhw.serverManage.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_INTELLECTUAL_PROJECT_TYPE")
public class IntellectualTypeBean {

    @TableId("id")
    private Integer id;

    @TableField("NAME")
    private String name;

    @TableField("INTELLECTUALTYPE")
    private String intellectualType;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
