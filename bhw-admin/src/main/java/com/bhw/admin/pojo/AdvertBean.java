package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_ADVERT")
public class AdvertBean {

    @TableId("ID")
    private Integer id;

    @TableField("TITLE")
    private String title;

    @TableField("KEYWORD")
    private String keyword;

    @TableField("IMGURL")
    private String imgUrl;

    @TableField("ORDERNO")
    private Integer orderNo;

    @TableField("STATUS")
    private Integer status;

    @TableField("ADVERTTYPE")
    private Integer advertType;

    @TableField("TYPEID")
    private Integer typeId;

    @TableField("BACKUSERID")
    private Integer backUserId;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
