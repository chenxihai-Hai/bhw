package com.bhw.bhwtechology.dto;

import lombok.Data;

@Data
public class ServerListPageReq {
   private Integer checkType;
   private Integer businessType;
   private  String startTime;
   private String endTime;
   private String techCode;
   private String techName;
   private Integer status;
}
