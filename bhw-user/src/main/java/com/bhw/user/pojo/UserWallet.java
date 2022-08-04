package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_USER_WALLET")
public class UserWallet {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("TREMAINING")
    private String tRemaining;

    @TableField("TFREEZE")
    private String tFreeze;

    @TableField("MONEYREMAINING")
    private String moneyRemaining;

    @TableField("MONEYFREEZE")
    private String moneyFreeze;

    @TableField("ORDERIDTEMP")
    private String orderIdTemp;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
