package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_ROLE")
public class BackRole {         //后台角色
    @TableId("id")
    private Integer id;

    @TableField("ROLENAME")
    private String roleName;

    @TableField("ROLEDESC")
    private String roleDesc;   //角色描述

    @TableField("OPERATEID")
    private Integer operateId;  //操作识别符

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
