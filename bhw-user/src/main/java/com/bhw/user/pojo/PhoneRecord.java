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
@TableName(value = "T_PHONE_RECORD")
public class PhoneRecord  {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("NEWPHONE")
    private String newPhone;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

}
