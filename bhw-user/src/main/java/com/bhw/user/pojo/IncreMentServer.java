package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_INCREMENT_SERVER")
public class IncreMentServer extends Model {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("SERVERID")
    private Integer serverId;

    @TableField("EXPIRETIME")
    private Date expireTime;

    @TableField("STATUS")
    private Integer status;

    @TableField("CREATETIME")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
