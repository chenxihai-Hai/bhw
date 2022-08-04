package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@TableName("T_PATENT")
@Data
public class PatentBean implements Serializable {       //专利列表
    @TableId
    private Integer id;

    @TableField("SCHOOLID")
    private Integer schoolid;          //院校编号

    @TableField("PATENTCODE")          //专利代码
    private String patentcode;

    @TableField("PATENTNAME")
    private String patentname;

    @TableField("SHENQINGDATE")
    private String shenqingdate;

    @TableField("PATENTIMG")           //专利图片
    private String patentimg;

    @TableField("CONTENT")
    private String content;

    @TableField("DAILIREN")            //代理人
    private String dailiren;

    @TableField("DAILIJIGOU")           //代理机构
    private String dailiJigou;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private List<PatentBean> list = new ArrayList<PatentBean>();
}
