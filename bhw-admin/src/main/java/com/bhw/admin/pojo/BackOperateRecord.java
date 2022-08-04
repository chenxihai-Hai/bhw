package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_BACK_OPERATE_RECORD")
public class BackOperateRecord {     //后台操作记录

    @TableId("ID")
    private Integer id;

    @TableField("OPERATENAME")
    private String operateName;

    @TableField("OPERATEID")
    private Integer operateId;

    @TableField("CONTENT")
    private String content;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
