package com.bhw.user.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserOrderVo implements Serializable {

    private String sellerName;

    private Integer sellerUserId;

    private Integer userId;

    private Integer productId;

    private String orderId;

    private String orderNum;

    private String itemName;

    private String itemLogo;

    private String unitPrice;

    private String totalPrice;

    private Integer itemNumber;

    private Integer status;

    private Integer payType;

    private Integer payStatus;

    private Integer refundStauts;

    private String createTime;

    private String updateTime;

    private String sendTime;

    private String orderVague;

    private String protocolUrl;

    private Integer addressId;

    private String remark;

    private Integer dealType;

    private String buyerName;

    private String vipType;
}
