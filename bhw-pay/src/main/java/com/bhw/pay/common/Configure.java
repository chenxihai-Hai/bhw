package com.bhw.pay.common;


public class Configure {


	//受理模式下给子商户分配的子商户号
	private static String subMchID = "";

	//HTTPS证书的本地路径
	private static String certLocalPath = "/usr/etc/apiclient_cert.p12";

	//HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = "1263716501";

	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;
	
	//接收微信支付异步通知回调地址  
	//private static String notify_url=AppConfig.getItemValue("wxpay.notify.url"); TCLOUDTODO _________________
	private static String notify_url="http://pay.duoxiwa.com/pay/WXpay/asynnotify";
	
	//取值如下：JSAPI，NATIVE，APP，WAP,详细说明见
	private static String trade_type="NATIVE";
	
	//private static String ip = AppConfig.getItemValue("wxpay.request.ip"); TCLOUDTODO  _________________
	private static String ip = "120.24.237.90";//改成duoxiwa服务ip 

	
	//以下是几个API的路径：
	//1）被扫支付API
	public static String PAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//2）被扫支付查询API
	public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

	//3）退款API
	public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	//4）退款查询API
	public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

	//5）撤销API
	public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

	//6）下载对账单API
	public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

	//7) 统计上报API
	public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.tcloud.pay.wxpaysdk.common.HttpsRequest";


	public static void setSubMchID(String subMchID) {
		Configure.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		Configure.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		Configure.certPassword = certPassword;
	}

	public static void setIp(String ip) {
		Configure.ip = ip;
	}


	public static String getSubMchid(){
		return subMchID;
	}
	
	public static String getCertLocalPath(){
		return certLocalPath;
	}
	
	public static String getCertPassword(){
		return certPassword;
	}

	public static String getIP(){
		return ip;
	}

	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

	public static String getNotify_url() {
		return notify_url;
	}

	public static void setNotify_url(String notify_url) {
		Configure.notify_url = notify_url;
	}

	public static String getTrade_type() {
		return trade_type;
	}

	public static void setTrade_type(String trade_type) {
		Configure.trade_type = trade_type;
	}
	
}
