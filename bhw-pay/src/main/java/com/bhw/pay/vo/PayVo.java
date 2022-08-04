package com.bhw.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayVo implements Serializable{


	private String appid;
	
	private String partnerid;
	
	private String prepayid;
	
	private String packagep;
	
	private String noncestr;
	
	private String timestamp;

	private String codeUrl;
	
	private String sign;

}
