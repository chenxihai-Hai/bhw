package com.bhw.user.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "T_INCREMENT_SERVER_RECORD")
public class IncreMentServerRecord extends Model {

    @TableId("id")
    private Integer id;

    private Integer incrementId;

    private String createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
