package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("T_ROLE_MENU")
public class BackRoleMenu {     //后台角色菜单
    @TableId("id")
    private Integer id;

    @TableField("ROLEID")
    private Integer roleId;

    @TableField(exist = false)
    private String roleName;

    @TableField("MENUID")
    private Integer menuId;

    @TableField(exist = false)
    private String menuName;

    @TableField(exist = false)
    private String menuUrl;    //菜单地址

    @TableField(exist = false)
    private String icon;

    @TableField(exist = false)
    private String iconClass;   //图标类

    @TableField(exist = false)
    private Integer parentId;    //父节点

    @TableField(exist = false)
    private Integer levels;     //等级

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;
}
