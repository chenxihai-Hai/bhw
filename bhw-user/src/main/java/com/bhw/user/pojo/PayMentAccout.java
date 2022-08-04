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
@TableName(value = "T_PAYMENT_ACCOUT")
public class PayMentAccout {
    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("IDCADR")
    private String idCard;

    @TableField("BANKTYPE")
    private Integer bankType;

    @TableField("BANKCARD")
    private String bankCard;

    @TableField("PHONE")
    private String phone;

    @TableField("STATUS")
    private Integer status;

    @TableField("PAYPASSWORD")
    private String payPassWord;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;


}
