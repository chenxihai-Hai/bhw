package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "T_USER")
public class UserMbBean extends Model {

    @TableId("id")
    private Integer id;

    /**
     * 用户名称
     * */
    @TableField("USERNAME")
    private String userName;

    /**
     * 头像
     * */
    @TableField("PHOTO")
    private String photo;
    /**
     * 描述
     */
    @TableField("REMARK")
    private String remark;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
