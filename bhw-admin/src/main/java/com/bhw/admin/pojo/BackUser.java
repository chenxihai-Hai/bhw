package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_BACK_USER")
public class BackUser {     //后台用户
    @TableId("id")
    private Integer id;

    @TableField("ACCOUNTNAME")
    private String accountName;    //账户名

    @TableField("USERNAME")
    private String userName;    //用户名

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("PASSWORD")
    private String passWord;

    @TableField("STATUS")
    private Integer status;

    @TableField("ACCOUNTTYPE")
    private Integer accountType;   //账户类型

    @TableField("ROLEID")
    private Integer roleId;

    @TableField(exist = false)
    private String roleName;

    @TableField("PARENTID")
    private Integer parentId;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
