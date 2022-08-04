package com.bhw.fiscalTax.dto;

import lombok.Data;

@Data
public class ServerListPageReq {
   private Integer checkType;
   private Integer businessType;
   private  String startTime;
   private String endTime;
   private String publisher;
   private Integer auditStatus;
   private Integer status;
}
