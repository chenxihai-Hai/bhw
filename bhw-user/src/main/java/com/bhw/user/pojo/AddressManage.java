package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_ADDRESS_MANAGE")
public class AddressManage {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("NICKNAME")
    private String nickName;

    @TableField("AREA")
    private Integer area;

    @TableField("ADDRESS")
    private String address;

    @TableField("ZIPCODE")
    private String zipCode;

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("DEFAULTFLAG")
    private String defaultFlag;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private String cityName;

    @TableField(exist = false)
    private String provinceName;
}
