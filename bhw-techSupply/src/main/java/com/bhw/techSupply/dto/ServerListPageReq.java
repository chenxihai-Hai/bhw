package com.bhw.techSupply.dto;

import lombok.Data;

@Data
public class ServerListPageReq {
   private Integer checkType;
   private Integer businessType;
   private  String startTime;
   private String endTime;
   private Integer status;
}
