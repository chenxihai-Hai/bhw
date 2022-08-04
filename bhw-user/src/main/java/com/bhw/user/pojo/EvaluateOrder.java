package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_EVALUATE_ORDER")
public class EvaluateOrder {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("ORDERID")
    private String orderId;

    @TableField("PRODUCTID")
    private Integer productId;

    @TableField("REMARK")
    private String remark;

    @TableField("PRODUCTGRADE")
    private Integer productGrade;

    @TableField("IMGS")
    private String imgs;

    @TableField("PACKGRADE")
    private Integer packGrade;

    @TableField("SPEED")
    private Integer speed;

    @TableField("SERVICEGRADE")
    private Integer serviceGrade;

    @TableField("TAGS")
    private String tags;

    @TableField("anonymity")
    private Integer ANONYMITY;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value="UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String userPhoto;

    @TableField(exist = false)
    private Integer praise;

    @TableField(exist = false)
    private Integer commMiddle;

    @TableField(exist = false)
    private Integer negative;

    @TableField(exist = false)
    private Integer imgnum;

    @TableField(exist = false)
    private Integer grade;
}
