package com.bhw.serverManage.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bhw.common.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "T_INTELLECTUAL_PROJECT")
public class ServerProjectBean {

    @TableId("id")
    private Integer id;

    @TableField("USERID")
    private Integer userId;

    @TableField("PROJECTNAME")
    private String projectName;

    @TableField("PROJECTCODE")
    private String projectCode;

    @TableField("UNITPRICE")
    private String unitPrice;

    @TableField("PROJECTTYPE")
    private Integer projectType;

    @TableField("INDUSTRYID")
    private Integer industryId;

    @TableField("PATENTTYPE")
    private Integer patentType;

    @TableField("DEALTYPE")
    private Integer dealType;

    @TableField("PROJECTNUMBER")
    private Integer projectNumber;

    @TableField("PROJECTUNIT")
    private String projectUnit;

    @TableField("CITYID")
    private Integer cityId;

    @TableField("FREIGHT")
    private String freight;

    @TableField("INTRODUCE")
    private String introduce;

    @TableField("HOMEIMG")
    private String homeImg;

    @TableField("IMGS")
    private String imgs;

    @TableField("VIDEOURL")
    private String videoUrl;

    @TableField("PROJECTTAG")
    private String projectTag;

    @TableField("PROTOCOLURL")
    private String protocolUrl;

    @TableField("DETAILS")
    private String details;

    @TableField("STATUS")
    private Integer status;

    @TableField("NEEDTYPE")
    private Integer needType;

    @TableField(value="CREATETIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value="UPDATETIME",fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("CODENUMBER")
    private String codeNumber;

    @TableField("INDATE")
    private String indate;

    @TableField("SOURCE")
    private String source;

    @TableField("AUDITSTATUS")
    private Integer auditStatus;


    @TableField("BOUTIQUE")
    private Integer boutique;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String contactPhone;

    @TableField(exist = false)
    private String buyerName;

    @TableField(exist = false)
    private String priceFilter;

    @TableField(exist = false)
    private String priceFilterEnd;

    @TableField(exist = false)
    private String cityName;

    @TableField(exist = false)
    private String provinceName;

    @TableField(exist = false)
    private String industryName;

    @TableField(exist = false)
    private String createTimeStr;

    @TableField(exist = false)
    private String updateTimeStr;

    @TableField(exist = false)
    private Integer priceType;

    public String getCreateTimeStr(){
        return DateUtil.formatDate(createTime,DateUtil.DATE_FORMAT_DATE_TIME);
    }

    public String getUpdateTimeStr(){
        return DateUtil.formatDate(updateTime,DateUtil.DATE_FORMAT_DATE_TIME);
    }
}
