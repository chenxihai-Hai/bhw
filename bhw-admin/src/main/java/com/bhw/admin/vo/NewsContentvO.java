package com.bhw.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
public class NewsContentvO {
    private String subject;

    private String subtitle;

    private String articleSource;

    private String webEditor;

    private String author;

    private String html;

    private String uploadTime;

    private Date createTime;
}
