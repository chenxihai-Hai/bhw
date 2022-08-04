package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_EVALUATE_BORKER")
public class BrokerEvaluate {     //经纪人评估
    @TableId("ID")
    private Integer id;

    @TableField("BORKERID")
    private Integer borkerId;

    @TableField("USERID")
    private Integer userId;

    @TableField("GRADE")
    private Integer grade;     //等级

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
