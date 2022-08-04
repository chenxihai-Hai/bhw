package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_SHOPPING_CART")
public class ShoppingCart {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("SELLERNAME")
    private String sellerName;

    @TableField("ITEMNAME")
    private String itemName;

    @TableField("ITEMLOGO")
    private String itemLogo;

    @TableField("UNITPRICE")
    private String unitPrice;

    @TableField("ITEMNUMBER")
    private Integer itemNumber;

    @TableField("TOTALPRICE")
    private String totalPrice;

    @TableField("PROJECTID")
    private String projectId;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
