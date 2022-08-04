package com.bhw.user.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_BHW_USER")
public class BhwUser extends Model {

    @TableId("id")
    private Integer id;

    @TableField("ACCOUNT")
    private String account;

    @TableField("USERNAME")
    private String userName;

    @TableField("PASSWORD")
    private String passWord;

    @TableField("NICKNAME")
    private String nickName;

    @TableField("PHONENO")
    private String phoneNo;

    @TableField("EMAIL")
    private String email;

    @TableField("IDCARD")
    private String idCard;

    @TableField("QQ")
    private String qq;

    @TableField("WX")
    private String wx;

    @TableField("PHOTO")
    private String photo;

    @TableField("SEX")
    private Integer sex;

    @TableField("BIRTHDAY")
    private String birthday;

    @TableField("MEMBER")
    private Integer member;

    @TableField("MEMBERGRADE")
    private Integer memberGrade;

    @TableField("STATUS")
    private Integer status;

    @TableField("SUBID")
    private Integer subId;

    @TableField("TYPE")
    private Integer type;

    @TableField("MERCHANTFLAG")
    private Integer merchantFlag;

    @TableField(value = "CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("AUDITTIME")
    private String auditTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private String createTimeSt;
}
