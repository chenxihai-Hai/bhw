package com.bhw.pay.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_WALLET_ORDER")
public class WalletOrder {

    @TableId("id")
    private Integer id;

    @TableField("ORDERID")
    private String orderId;

    @TableField("USERID")
    private Integer userId;

    @TableField("ORDERNUM")
    private String orderNum;

    @TableField("TOTALPRICE")
    private String totalPrice;

    @TableField("STATUS")
    private Integer status;

    @TableField("PAYTYPE")
    private Integer payType;

    @TableField("PAYSTATUS")
    private Integer payStatus;

    @TableField("DEALTYPE")
    private Integer dealType;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value="UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String productId;
}
