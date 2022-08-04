package com.bhw.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("T_LISTED_NOTICEINFO")
public class ListedNoticeBirefVo {
    @TableId("INFO_ID")
    private Integer infoId;

    @TableField("INFO_TITLE")
    private String infoTitle;

    @TableField("CONTENT")
    private String content;
}
