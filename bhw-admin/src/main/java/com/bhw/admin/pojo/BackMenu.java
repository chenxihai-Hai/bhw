package com.bhw.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("T_MENU")
public class BackMenu {  //后台菜单
    @TableId("MENUID")
    private Integer menuId;

    @TableField("MENUNAME")
    private String menuName;

    @TableField("MENUURL")
    private String menuUrl;

    @TableField("PARENTID")
    private Integer parentId;

    @TableField("LEVELS")
    private Integer levels;

    @TableField("ICON")
    private String icon;

    @TableField("ICONCLASS")
    private String iconClass;




}
