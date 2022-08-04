package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_WITHDRAW_USER")
public class UserWithdraw {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("TOTALPRICE")
    private String totalPrice;

    @TableField("ORDERIDTEMP")
    private String orderIdTemp;

    @TableField("STATUS")
    private Integer status;

    @TableField("BANKCARDID")
    private Integer bankCardId;

    @TableField("OPERATENAME")
    private String operateName;

    @TableField("OPERATEID")
    private Integer operateId;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String bankCard;

    @TableField(exist = false)
    private String bankName;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;
}
