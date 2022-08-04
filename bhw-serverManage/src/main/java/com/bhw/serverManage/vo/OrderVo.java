package com.bhw.serverManage.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderVo {

    private Integer id;

    private String orderId;

    private String orderNum;

    private String itemName;

    private String itemLogo;

    private String unitPrice;

    private Integer itemNumber;

    private String totalPrice;

    private Integer status;

    private Integer payType;

    private Integer payStatus;

    private Integer closeStatus;

    private String createTime;

    private String updateTime;

    private String productId;

    private Integer userId;

    private String buyerName;

    private String protocolUrl;

    private String sellerName;

    private Integer refundStatus;
}
