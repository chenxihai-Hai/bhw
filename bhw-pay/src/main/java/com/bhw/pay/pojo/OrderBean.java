package com.bhw.pay.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_ORDER")
public class OrderBean extends Model {

    @TableId("id")
    private Integer id;

    @TableField("ORDERID")
    private String orderId;

    @TableField("ORDERNUM")
    private String orderNum;

    @TableField(exist = false)
    private String itemName;

    @TableField(exist = false)
    private String itemLogo;

    @TableField("UNITPRICE")
    private String unitPrice;

    @TableField("ITEMNUMBER")
    private Integer itemNumber;

    @TableField("TOTALPRICE")
    private String totalPrice;

    @TableField("STATUS")
    private Integer status;

    @TableField("PAYTYPE")
    private Integer payType;

    @TableField("PAYSTATUS")
    private Integer payStatus;

    @TableField("CLOSESTATUS")
    private Integer closeStatus;

    @TableField("ADDRESSID")
    private Integer addressId;

    @TableField("REMARK")
    private String remark;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value="UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("SENDTIME")
    private Date sendTime;

    @TableField(exist = false)
    private String productId;

    @TableField(exist = false)
    private Integer userId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
