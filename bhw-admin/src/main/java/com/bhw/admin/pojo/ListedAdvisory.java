package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_LISTED_ADVISORY")
public class ListedAdvisory {

    @TableId("ID")
    private Integer id;

    @TableField("NAME")
    private String name;

    @TableField("PHONE")
    private String phone;

    @TableField("REMARK")
    private String remark;   //评论

    @TableField("OPREATENAME")
    private String opreateName;

    @TableField("OPREATEID")
    private Integer opreateId;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
