package com.bhw.admin.pojo;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_USER")
public class UserBean  {
    @TableId("ID")
    private Integer id;

    @TableField("USERNAME")
    private String userName;

    @TableField("PHONE")
    private String phone;

    @TableField("PHOTO")
    private String photo;

    @TableField("TYPE")
    private String type;

    @TableField("DUTYID")
    private Integer dutyId;

    @TableField("PROFESSIONID")
    private Integer professionId;

    @TableField("BROKERID")
    private Integer borkerId;

    @TableField("REMARK")
    private String remark;

    @TableField("CREATETIME")
    private Date createTime;

    @TableField("ISLISTED")
    private Integer isListed;
}
