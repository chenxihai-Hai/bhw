package com.bhw.admin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BrokerRegisterReq {

    private String phone;

    private String name;

    private Integer sex;

    private String email;

    private String seniority;

    private String mainAchieve;

    private Integer status;

    private Integer professionId;
}
