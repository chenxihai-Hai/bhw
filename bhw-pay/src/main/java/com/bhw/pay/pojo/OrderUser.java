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
@TableName(value = "T_ORDER_USER")
public class OrderUser extends Model {

    @TableId("id")
    private Integer id;

    @TableField("ORDERID")
    private String orderId;

    @TableField("USERID")
    private Integer userId;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
