package com.bhw.pay.vo;

import lombok.Data;

import java.io.Serializable;



@Data
public class OrderFormVo implements Serializable{

	private Long id;
	
	private String orderid;//订单号
	
	private Long infoid;//
	
	private Integer type;
	
	private Long publishid;
	
	private String username;
	
	private String title;

	private String begintime;

	private String infotype;
	
	private String serialnum;
	
	private String money;
	
	private Integer paytype;
	
	private Integer paystatus;
	
	private Integer remove;

	private String createtime;
	
}
