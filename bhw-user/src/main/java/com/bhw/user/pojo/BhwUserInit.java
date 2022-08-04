package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_BHWUSER_INIT")
public class BhwUserInit extends Model {
    @TableId("id")
    private Integer id;

    private Integer userId;

    private String passWord;

    private String payPassWord;

    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
