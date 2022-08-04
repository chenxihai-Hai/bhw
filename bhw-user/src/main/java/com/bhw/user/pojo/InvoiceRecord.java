package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_INVOICE_RECORD")
public class InvoiceRecord {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("ORDERID")
    private String orderId;

    @TableField("SELLERNAME")
    private String sellerName;

    @TableField("BAYNAME")
    private String bayName;

    @TableField("INVOICEURL")
    private String invoiceUrl;

    @TableField("TOTALPRICE")
    private String totalPrice;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
