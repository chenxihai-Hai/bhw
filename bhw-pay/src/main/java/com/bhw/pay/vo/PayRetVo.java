package com.bhw.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayRetVo implements Serializable{


	private String app_id;
	
	private String auth_app_id;
	
	private String buyer_id;//买家支付宝用户号
	
	private String buyer_logon_id;//买家支付宝账号
	
	private String buyer_pay_amount;//付款金额
	
	private String gmt_create;//交易创建时间
	
	private String gmt_payment;//交易付款时间
	
	private String trade_no;//支付宝交易号
	
	private String out_trade_no;//商户订单号
	
	private String out_biz_no;//商户业务号
	
	private String trade_status;//交易状态
	
	private String total_amount;//订单金额
	
	private String receipt_amount;//实收金额

	
	
}
