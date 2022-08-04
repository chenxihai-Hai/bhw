package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("T_REPORT")
@Data
public class ReportBean {      //报告列表

    @TableId
    private Integer id;

    @TableField("REPORTOBJECT")
    private Integer reportObject;

    @TableField("TITLE")
    private String title;

    @TableField("URL")
    private String url;

    @TableField("USERID")
    private Integer userId;

    @TableField("USERNAME")
    private String userName;

    @TableField("PHONE")
    private String phone;

    @TableField("EMAIL")
    private String email;

    @TableField("CONTENT")
    private String content;

    @TableField("STATUS")
    private Integer status;

    @TableField("DISPOSEUSERID")
    private Integer disposeUserId;

    @TableField("DISPOSEUSER")
    private String disposeUser;    //处理用户

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "DISPOSETIME",fill = FieldFill.UPDATE)
    public Date disposeTime;

}
