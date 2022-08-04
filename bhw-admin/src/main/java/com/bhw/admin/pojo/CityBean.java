package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "T_BAIDU_CITY")
public class CityBean {
    @TableId("CITY_ID")
    private Integer cityId;

    @TableField("CITY_NAME")
    private String cityName;

    @TableField("PROVINCE_ID")
    private Integer provinceId;

}
