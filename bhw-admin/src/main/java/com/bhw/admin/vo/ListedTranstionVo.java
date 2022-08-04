package com.bhw.admin.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ListedTranstionVo {

    private Integer id;

    private String client;

    private Integer closestatus;

    private String delister;

    private Date endTime;

    private Date startTime;

    private String listedName;

    private String listedNumber;

    private String contact;

    private String content;

    private String infoTitle;

    private String remark;

    private String tptPostion;

    private String tptTime;

    private Date transactionTime;

    private String notDelister;
}
