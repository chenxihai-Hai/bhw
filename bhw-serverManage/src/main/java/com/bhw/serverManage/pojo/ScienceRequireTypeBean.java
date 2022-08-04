package com.bhw.serverManage.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "T_SCIENCE_REQUIRE_TYPE")
public class ScienceRequireTypeBean {

    @TableId("id")
    private Integer id;

    @TableField("NAME")
    private String name;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private String createTime;
}
