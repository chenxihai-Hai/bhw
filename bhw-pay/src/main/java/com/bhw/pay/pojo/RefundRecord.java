package com.bhw.pay.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_REFUND_RECORD")
public class RefundRecord {
    @TableId("id")
    private Integer id;

    @TableField("ORDERID")
    private String orderId;

    @TableField("REFUNDORDERID")
    private String refundOrderId;

    @TableField("REFUNDUSERID")
    private Integer refundUserId;

    @TableField("REMARK")
    private String remark;

    @TableField("REFUNDPRICE")
    private String refundPrice;

    @TableField("REFUNDTYPE")
    private String refundType;

    @TableField("STATUS")
    private Integer status;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String itemName;

    @TableField(exist = false)
    private String itemLogo;

    @TableField(exist = false)
    private Integer payStatus;
}
