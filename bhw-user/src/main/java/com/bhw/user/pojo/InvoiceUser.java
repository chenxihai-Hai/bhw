package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_INVOICE_USER")
public class InvoiceUser {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("INVOICENAME")
    private String invoiceName;

    @TableField("TFN")
    private String tfn;

    @TableField("DEFAULTFLAG")
    private Integer defaultFlag;

    @TableField("COMPANYADDR")
    private String companyAddr;

    @TableField("TELEPHONE")
    private String telePhone;

    @TableField("BANKNAME")
    private String bankName;

    @TableField("BANKCODE")
    private String bankCode;

    @TableField("PHONE")
    private String phone;

    @TableField("EMAIL")
    private String email;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
