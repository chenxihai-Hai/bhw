package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "T_BAIDU_PROVINCE")
public class ProvinceBean {
    @TableId("PROVINCE_ID")
    private Integer provinceId;

    @TableField("PROVINCE_NAME")
    private String provinceName;

}
