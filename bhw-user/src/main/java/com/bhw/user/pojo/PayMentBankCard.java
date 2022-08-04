package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_PAYMENT_BANKCARD")
public class PayMentBankCard {
    @TableId("id")
    private Integer id;

    @TableField("PAYACCOUNTID")
    private Integer payAccountId;

    @TableField("BANKCARDTYPE")
    private Integer bankCardType;

    @TableField("BANKNAME")
    private String bankName;

    @TableField("BANKCARD")
    private String bankCard;

    @TableField("IDCARD")
    private String idCard;

    @TableField("MOBILE")
    private String mobile;

    @TableField("REALNAME")
    private String realName;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;


}
