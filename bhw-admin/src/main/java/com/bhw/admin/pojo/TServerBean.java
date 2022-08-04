package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("T_SERVER")
public class TServerBean extends Model {
    @TableId("id")
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("xgms")
    private String xgms;

    @TableField("xq")
    private String xq;

    @TableField("icon")
    private String icon;

    @TableField("URL")
    private String url;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
