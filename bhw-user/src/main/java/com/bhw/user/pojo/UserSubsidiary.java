package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_SUBSIDIARY_USER")
public class UserSubsidiary {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("UNITNAME")
    private  String unitName;

    @TableField("DUTY")
    private  String duty;

    @TableField("QUALIFICATION")
    private String qualification;

    @TableField("SCHOOL")
    private  String school;

    @TableField("EDUCATION")
    private  String education;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

}
