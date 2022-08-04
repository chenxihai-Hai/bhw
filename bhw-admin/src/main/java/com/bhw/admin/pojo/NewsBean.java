package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_NEWS")
public class NewsBean{        //新闻列表
    @TableId("ID")
    private Integer id;

    @TableField("SUBJECT")
    private String subject;      //主题

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("TYPE")
    private String type;    //类型

    @TableField("ARTICLESOURCE")
    private String articleSource;    //文章来源

    @TableField("WEBEDITOR")
    private String webEditor;

    @TableField("AUTHOR")
    private String author;

    @TableField("SUBTITLE")
    private String subTitle;   //小标题

    @TableField("IMG")
    private String img;

    @TableField("HTML")
    private String html;

    @TableField("USERNAME")
    private String userName;

    @TableField("USERID")
    private Integer userId;

    @TableField("UPLOADTIME")
    private String uploadTime;
}
